package com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.ITokenValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenHeaderValidatorImpl implements ITokenValidatorService {

    private static final String EXPECTED_TYPE = "JWT";
    private static final String EXPECTED_ALG = "HS256";
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    public void validate(String token) {
        try {
            String[] parts = token.split("\\.");
            String headerJson = this.decodeHeader(parts[0]);
            this.validateHeader(headerJson);
        } catch (Exception e) {
            log.error("Invalid JWT header: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.header.validation.error");
        }
    }

    private @NotNull String decodeHeader(String headerPart) {
        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(headerPart);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            log.error("Invalid JWT header encoding: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.header.decode.error");
        }
    }

    private void validateHeader(String headerJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> header = mapper.readValue(headerJson, new TypeReference<>() {
            });

            if (!EXPECTED_TYPE.equals(header.get("typ"))) {
                throw this.exceptionShortComponent.tokenInvalidException("auth.token.header.type.invalid");
            }

            if (!EXPECTED_ALG.equals(header.get("alg"))) {
                throw this.exceptionShortComponent.tokenInvalidException("auth.token.header.alg.invalid");
            }
        } catch (JsonProcessingException e) {
            log.error("Invalid JWT header format: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.header.format.invalid");
        }
    }
}
