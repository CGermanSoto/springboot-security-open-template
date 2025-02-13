package com.spacecodee.springbootsecurityopentemplate.security.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.stereotype.Component;

@Component
public class CustomSecurityHeadersConfigurer
        extends AbstractHttpConfigurer<CustomSecurityHeadersConfigurer, HttpSecurity> {

    @Override
    public void configure(@NotNull HttpSecurity http) throws Exception {
        Customizer<HeadersConfigurer<HttpSecurity>> headersCustomizer = headers -> headers
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'none';")) // Strict CSP for API
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                .httpStrictTransportSecurity(hsts -> hsts
                        .includeSubDomains(true)
                        .maxAgeInSeconds(31536000));

        http.headers(headersCustomizer);
    }

}