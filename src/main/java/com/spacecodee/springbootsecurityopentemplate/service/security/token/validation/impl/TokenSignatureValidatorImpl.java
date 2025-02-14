package com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.ITokenValidatorService;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenSignatureValidatorImpl implements ITokenValidatorService {

    private final ExceptionShortComponent exceptionShortComponent;

    private final JwtParser jwtParser;

    @Override
    public void validate(String token) {
        try {
            this.jwtParser.parseSignedClaims(token);
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw exceptionShortComponent.jwtTokenSignatureException("auth.token.signature.invalid");
        }
    }
}
