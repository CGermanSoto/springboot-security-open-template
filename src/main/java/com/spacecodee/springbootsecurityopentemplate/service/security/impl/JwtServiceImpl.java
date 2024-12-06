package com.spacecodee.springbootsecurityopentemplate.service.security.impl;

import com.spacecodee.springbootsecurityopentemplate.data.record.TokenClaims;
import com.spacecodee.springbootsecurityopentemplate.data.record.TokenValidationResult;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.TokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtServiceImpl implements IJwtService {

    private final IJwtTokenService jwtTokenService;

    @Value("${security.jwt.expiration-in-minutes}")
    private long expirationInMinutes;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public JwtServiceImpl(IJwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    // Generate a JWT token with the given userDetails details and extra claims
    @Override
    public String generateToken(@NonNull UserDetails userDetails, Map<String, Object> extraClaims) {
        return buildToken(new TokenClaims(userDetails.getUsername(), extraClaims));
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
            log.warn("Authorization header is missing or does not contain a Bearer token");
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
            log.warn("Expiration date: {}", expiration);
            return expiration.before(new Date());
        } catch (TokenExpiredException e) {
            log.warn("The token is already expired, this is a warning", e);
            return true;
        }
    }

    // JwtServiceImpl.java - Implement refresh method
    @Override
    public String refreshToken(String oldToken, UserDetails userDetails) {
        try {
            Claims claims = extractAllClaims(oldToken);
            return buildToken(new TokenClaims(userDetails.getUsername(), claims));
        } catch (Exception e) {
            log.warn("Error refreshing token", e);
            return null;
        }
    }

    @Override
    public TokenValidationResult validateAndRefreshToken(String jwt, String locale) {
        try {
            var claims = extractAllClaims(jwt);
            var expiration = claims.getExpiration();
            var isExpired = expiration.before(new Date());

            if (isExpired) {
                log.info("Token expired, attempting refresh");
                return this.handleExpiredToken(jwt, claims, locale);
            }

            return new TokenValidationResult(jwt, false);
        } catch (TokenExpiredException e) {
            log.error("Error validating token", e);
            throw new TokenExpiredException("token.expired", locale);
        }
    }

    @Contract("_, _, _ -> new")
    private @NotNull TokenValidationResult handleExpiredToken(String jwt, Claims claims, String locale) {
        try {
            // Delete expired token
            this.jwtTokenService.deleteByToken(locale, jwt);

            // Generate new token with existing claims using buildToken
            String newToken = buildToken(new TokenClaims(null, claims));

            return new TokenValidationResult(newToken, true);
        } catch (Exception e) {
            log.error("Error refreshing token", e);
            throw new TokenExpiredException("token.refresh.failed", locale);
        }
    }

    // Generate the secret key
    @NonNull
    private SecretKey generateKey() {
        var passDecoder = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(passDecoder);
    }

    private String buildToken(@NotNull TokenClaims tokenClaims) {
        var issuedAt = new Date(System.currentTimeMillis());
        var expiration = new Date(System.currentTimeMillis() + (this.expirationInMinutes * 60 * 1000));

        var builder = Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .issuedAt(issuedAt)
                .expiration(expiration);

        if (tokenClaims.subject() != null) {
            builder.subject(tokenClaims.subject());
        }

        if (tokenClaims.claims() != null) {
            builder.claims(tokenClaims.claims());
        }

        return builder.signWith(this.generateKey(), Jwts.SIG.HS256).compact();
    }

    // Extract all claims from the JWT
    private Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(this.generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}