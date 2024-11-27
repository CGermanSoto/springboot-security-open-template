package com.spacecodee.springbootsecurityopentemplate.security.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class JwtServiceImpl implements IJwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private long expirationInMinutes;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    private final Logger logger = Logger.getLogger(JwtServiceImpl.class.getName());

    // Generate a JWT token with the given userDetails details and extra claims
    @Override
    public String generateToken(@NonNull UserDetails userDetails, Map<String, Object> extraClaims) {
        var issuedAt = new Date(System.currentTimeMillis());
        var expiration = new Date((this.expirationInMinutes * 60 * 1000) + issuedAt.getTime());

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(userDetails.getUsername()) // Set the subject to the username
                .issuedAt(issuedAt) // Set the issued at date
                .expiration(expiration) // Set the expiration date
                .claims(extraClaims) // Set the extra claims
                .signWith(this.generateKey(), Jwts.SIG.HS256) // Sign the token with the secret key and the HS256
                // algorithm
                .compact();
    }

    // Extract the username from the JWT
    @Override
    public String extractUsername(String jwt) {
        return this.extractAllClaims(jwt).getSubject();
    }

    @Override
    public String extractJwtFromRequest(@NotNull HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is null or empty
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")
                || !StringUtils.hasText(authorizationHeader)) {
            this.logger.log(Level.WARNING, "Authorization header is missing or does not contain a Bearer token");
            return null;
        }

        // Return the token without the "Bearer " prefix
        return authorizationHeader.split(" ")[1];
    }

    // Extract the expiration date from the JWT
    @Override
    public Date extractExpiration(String jwt) {
        return this.extractAllClaims(jwt)
                .getExpiration();
    }

    @Override
    public boolean isTokenExpired(String jwt) {
        try {
            var expiration = this.extractExpiration(jwt);
            return expiration.before(new Date());
        } catch (Exception e) {
            this.logger.log(Level.WARNING, "The token is already expired, this is a warning", e);
        }
        return false;
    }

    // JwtServiceImpl.java - Implement refresh method
    @Override
    public String refreshToken(String oldToken, UserDetails userDetails) {
        try {
            // Extract existing claims from old token
            Claims claims = extractAllClaims(oldToken);
            // Generate new token with existing claims
            return Jwts.builder()
                    .header()
                    .type("JWT")
                    .and()
                    .claims(claims)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + (this.expirationInMinutes * 60 * 1000)))
                    .signWith(this.generateKey(), Jwts.SIG.HS256)
                    .compact();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error refreshing token", e);
            return null;
        }
    }

    // Extract all claims from the JWT
    private Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(this.generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    // Generate the secret key
    @NonNull
    private SecretKey generateKey() {
        var passDecoder = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(passDecoder);
    }
}