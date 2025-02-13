package com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.ITokenValidatorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenClaimsValidatorImpl implements ITokenValidatorService {

    private static final List<String> REQUIRED_CLAIMS = List.of("sub", "iat", "exp");
    private final ExceptionShortComponent exceptionShortComponent;
    private final JwtParser jwtParser;

    private static final String AUTH_TOKEN_CLAIMS_INVALID = "auth.token.claims.invalid";

    @Override
    public void validate(String token) {
        try {
            Claims claims = this.jwtParser.parseSignedClaims(token).getPayload();
            this.validateRequiredClaims(claims);
            this.validateClaimsFormat(claims);
            this.validateClaimsExpiration(claims);
        } catch (Exception e) {
            log.error("Error validating token claims: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException(AUTH_TOKEN_CLAIMS_INVALID);
        }
    }

    private void validateRequiredClaims(Claims claims) {
        for (String claim : REQUIRED_CLAIMS) {
            if (claims.get(claim) == null) {
                log.error("Missing required claim: {}", claim);
                throw this.exceptionShortComponent.tokenInvalidException("auth.token.claim.missing", claim);
            }
        }
    }

    private void validateClaimsFormat(@NotNull Claims claims) {
        if (claims.getSubject() == null) {
            log.error("Invalid subject claim format");
            throw this.exceptionShortComponent.tokenInvalidException(AUTH_TOKEN_CLAIMS_INVALID);
        }

        if (claims.getIssuedAt() == null) {
            log.error("Invalid issuedAt claim format");
            throw this.exceptionShortComponent.tokenInvalidException(AUTH_TOKEN_CLAIMS_INVALID);
        }

        if (claims.getExpiration() == null) {
            log.error("Invalid expiration claim format");
            throw this.exceptionShortComponent.tokenInvalidException(AUTH_TOKEN_CLAIMS_INVALID);
        }
    }

    private void validateClaimsExpiration(@NotNull Claims claims) {
        Date now = new Date();
        if (claims.getExpiration().before(now)) {
            log.error("Token has expired");
            throw this.exceptionShortComponent.tokenExpiredException("auth.token.expired");
        }

        if (claims.getIssuedAt().after(now)) {
            log.error("Token issued in the future");
            throw this.exceptionShortComponent.tokenInvalidException(AUTH_TOKEN_CLAIMS_INVALID);
        }
    }

}
