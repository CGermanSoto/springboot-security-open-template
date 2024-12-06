package com.spacecodee.springbootsecurityopentemplate.service.security.impl;

import com.spacecodee.springbootsecurityopentemplate.data.record.TokenClaims;
import com.spacecodee.springbootsecurityopentemplate.data.record.TokenValidationResult;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.TokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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
    private final ExceptionShortComponent exceptionShortComponent;

    @Value("${security.jwt.expiration-in-minutes}")
    private long expirationInMinutes;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public JwtServiceImpl(IJwtTokenService jwtTokenService, ExceptionShortComponent exceptionShortComponent) {
        this.jwtTokenService = jwtTokenService;
        this.exceptionShortComponent = exceptionShortComponent;
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
            Claims claims = this.extractAllClaims(oldToken);
            return buildToken(new TokenClaims(userDetails.getUsername(), claims));
        } catch (Exception e) {
            log.warn("Error refreshing token", e);
            return null;
        }
    }

    @Override
    public TokenValidationResult validateToken(String jwt, String locale) {
        try {
            this.extractAllClaims(jwt);
            return new TokenValidationResult(jwt, false);
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException e) {
            log.info("JWT validation failed, deleting token: {}", e.getMessage());
            this.jwtTokenService.deleteByToken(locale, jwt);
            throw this.exceptionShortComponent.tokenExpiredException("token.expired", locale);
        } catch (Exception e) {
            log.error("Unexpected error validating token: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("token.invalid", locale);
        }
    }

    @Contract("_, _, _ -> new")
    private @NotNull TokenValidationResult handleExpiredToken(String jwt, Claims claims, String locale) {
        try {
            // Delete expired token
            this.jwtTokenService.deleteByToken(locale, jwt);

            // Generate a new token with existing claims using buildToken
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