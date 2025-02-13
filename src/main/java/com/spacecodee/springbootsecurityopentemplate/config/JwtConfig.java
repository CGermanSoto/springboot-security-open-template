package com.spacecodee.springbootsecurityopentemplate.config;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.key.IJwtKeyService;

import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final IJwtKeyService jwtKeyService;
    private final ExceptionShortComponent exceptionShortComponent;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @PostConstruct
    public void validateConfiguration() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            if (!this.jwtKeyService.isValidKey(keyBytes)) {
                throw this.exceptionShortComponent.tokenInvalidException("auth.token.key.invalid");
            }
        } catch (IllegalArgumentException e) {
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.key.error", e.getMessage());
        }
    }

    @Bean
    JwtParser jwtParser() {
        return Jwts.parser()
                .verifyWith(this.jwtKeyService.generateKey())
                .build();
    }
}
