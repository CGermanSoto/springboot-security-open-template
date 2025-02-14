package com.spacecodee.springbootsecurityopentemplate.service.security.token.builder.impl;

import com.spacecodee.springbootsecurityopentemplate.data.record.TokenClaims;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.builder.ITokenBuilderService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenBuilderServiceImpl implements ITokenBuilderService {

    @Value("${security.jwt.expiration-in-minutes}")
    private int expirationInMinutes;

    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    public String build(TokenClaims tokenClaims, SecretKey key) {
        try {
            return this.buildToken(tokenClaims, key);
        } catch (Exception e) {
            log.error("Error building token: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenUnexpectedException("auth.token.error");
        }
    }

    private String buildToken(@NotNull TokenClaims tokenClaims, SecretKey key) {
        var now = Instant.now();
        var expiration = now.plusSeconds(this.expirationInMinutes * 60L);

        var builder = Jwts.builder()
                .header().type("JWT").and()
                .id(this.generateTokenId(tokenClaims.subject(), now))
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .issuer("document-management-system");

        if (tokenClaims.subject() != null) {
            builder.subject(tokenClaims.subject());
        }

        if (tokenClaims.claims() != null) {
            Map<String, Object> cleanedClaims = new HashMap<>(tokenClaims.claims());
            // Remove any potential permission claims
            cleanedClaims.remove("authorities");
            // Keep only essential claims
            cleanedClaims.keySet().removeIf(k -> !List.of("userId", "role", "type").contains(k));

            builder.claims(cleanedClaims);
        }

        return builder.signWith(key, Jwts.SIG.HS256).compact();
    }

    private @NotNull String generateTokenId(String username, @NotNull Instant issuedAt) {
        return String.format("%s_%d",
                username,
                issuedAt.toEpochMilli());
    }
}
