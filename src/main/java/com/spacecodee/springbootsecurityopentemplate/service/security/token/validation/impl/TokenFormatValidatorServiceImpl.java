package com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.ITokenValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenFormatValidatorServiceImpl implements ITokenValidatorService {

    private static final int EXPECTED_PARTS = 3;
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    public void validate(String token) {
        if (!StringUtils.hasText(token)) {
            throw exceptionShortComponent.tokenInvalidException("auth.token.empty");
        }

        String[] parts = token.split("\\.");
        if (parts.length != EXPECTED_PARTS) {
            throw exceptionShortComponent.jwtTokenInvalidFormatException("auth.token.invalid.format");
        }
    }
}
