package com.spacecodee.springbootsecurityopentemplate.service.security.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacecodee.springbootsecurityopentemplate.data.record.TokenClaims;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtProviderService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtProviderServiceImpl implements IJwtProviderService {

    private final ExceptionShortComponent exceptionShortComponent;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-in-minutes}")
    private long expirationInMinutes;

    public JwtProviderServiceImpl(ExceptionShortComponent exceptionShortComponent) {
        this.exceptionShortComponent = exceptionShortComponent;
    }

    @Override
    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        if (userDetails == null) {
            log.error("Attempt to generate token for null user");
            throw this.exceptionShortComponent.invalidParameterException("auth.user.null", "en");
        }

        return buildToken(new TokenClaims(userDetails.getUsername(), extraClaims));
    }

    public String buildToken(@NotNull TokenClaims tokenClaims) {
        var issuedAt = new Date(System.currentTimeMillis());
        var expiration = new Date(System.currentTimeMillis() + (this.expirationInMinutes * 60 * 1000));

        var builder = Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .issuedAt(issuedAt) // Set new issuedAt
                .expiration(expiration); // Set new expiration

        if (tokenClaims.subject() != null) {
            builder.subject(tokenClaims.subject());
        }

        if (tokenClaims.claims() != null) {
            // Remove date-related claims from old token
            Map<String, Object> cleanedClaims = new HashMap<>(tokenClaims.claims());
            cleanedClaims.remove("exp");
            cleanedClaims.remove("iat");

            builder.claims(cleanedClaims);
        }

        return builder.signWith(this.generateKey(), Jwts.SIG.HS256).compact();
    }

    @Override
    public String extractUsername(String jwt) {
        return this.extractClaims(jwt).getSubject();
    }

    @Override
    public String extractUsernameFromRequest(HttpServletRequest request) {
        var jwtExtracted = this.extractJwtFromRequest(request);
        if (jwtExtracted == null) {
            log.error("Attempt to extract username from null JWT");
            throw this.exceptionShortComponent.invalidParameterException("auth.jwt.null", "en");
        }

        return this.extractUsername(jwtExtracted);
    }

    @Override
    public String extractJwtFromRequest(HttpServletRequest request) {
        if (request == null) {
            log.error("Attempt to extract token from null request");
            throw this.exceptionShortComponent.invalidParameterException("auth.request.null", "en");
        }

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public Date extractExpiration(String jwt) {
        return extractClaims(jwt).getExpiration();
    }

    @Override
    public Claims extractClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public Claims extractClaimsWithoutValidation(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT format");
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> claimsMap = mapper.readValue(payload, new TypeReference<>() {
            });

            return Jwts.claims()
                    .add(claimsMap)
                    .build();

        } catch (Exception e) {
            log.error("Error extracting claims from JWT: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenExpiredException("token.expired", "en");
        }
    }

    @Override
    public boolean isTokenValid(String jwt) {
        try {
            Claims claims = extractClaims(jwt);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }

    private @NotNull SecretKey generateKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}