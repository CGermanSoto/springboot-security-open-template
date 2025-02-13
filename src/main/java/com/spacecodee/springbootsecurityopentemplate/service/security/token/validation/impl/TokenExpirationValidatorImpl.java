package com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.ITokenValidatorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenExpirationValidatorImpl implements ITokenValidatorService {

    private final ExceptionShortComponent exceptionShortComponent;
    private final JwtParser jwtParser;

    @Override
    public void validate(String token) {
        try {
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();
            if (claims.getExpiration().before(new Date())) {
                throw exceptionShortComponent.tokenExpiredException("auth.token.expired");
            }
        } catch (ExpiredJwtException e) {
            log.error("Token has expired: {}", e.getMessage());
            throw exceptionShortComponent.tokenExpiredException("auth.token.expired");
        }
    }
}
