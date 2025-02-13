package com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.impl;

import com.spacecodee.springbootsecurityopentemplate.service.security.token.validation.ITokenValidatorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenValidationChainImpl {

    private final TokenFormatValidatorServiceImpl tokenFormatValidator;
    private final TokenSignatureValidatorImpl tokenSignatureValidator;
    private final TokenExpirationValidatorImpl tokenExpirationValidator;
    private final TokenClaimsValidatorImpl tokenClaimsValidator;
    private final TokenHeaderValidatorImpl tokenHeaderValidator;

    private List<ITokenValidatorService> validators;

    @PostConstruct
    public void initializeValidators() {
        this.validators = new ArrayList<>();
        this.validators.add(this.tokenHeaderValidator); // Add header validation first
        this.validators.add(this.tokenFormatValidator); // Then format
        this.validators.add(this.tokenSignatureValidator); // Then signature
        this.validators.add(this.tokenExpirationValidator); // Then expiration
        this.validators.add(this.tokenClaimsValidator); // Finally, claims
    }

    public void validateToken(String token) {
        this.validators.forEach(validator -> validator.validate(token));
    }
}
