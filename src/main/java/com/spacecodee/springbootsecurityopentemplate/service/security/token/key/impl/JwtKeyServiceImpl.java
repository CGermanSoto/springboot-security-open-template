package com.spacecodee.springbootsecurityopentemplate.service.security.token.key.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.key.IJwtKeyService;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtKeyServiceImpl implements IJwtKeyService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    public @NotNull SecretKey generateKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            log.error("Invalid key format: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenUnexpectedException("auth.token.key.invalid");
        } catch (Exception e) {
            log.error("Error generating secret key: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenUnexpectedException("auth.token.key.error");
        }
    }

    @Override
    public boolean isValidKey(byte @NotNull [] key) {
        try {
            var secretKeyToValidate = Keys.hmacShaKeyFor(key);
            return secretKeyToValidate.getEncoded().length >= 32;
        } catch (Exception e) {
            log.debug("Invalid key format: {}", e.getMessage());
            return false;
        }
    }

}
