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

    /**
     * Configures custom security headers for the application.
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs while configuring the security headers
     */
    @Override
    public void configure(@NotNull HttpSecurity http) throws Exception {
        Customizer<HeadersConfigurer<HttpSecurity>> headersCustomizer = headers -> headers
                // Set a strict Content Security Policy (CSP) for the API
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'none';")) // Strict CSP for API
                // Deny framing of the application
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                // Enable HTTP Strict Transport Security (HSTS) with a max age of 1 year and include subdomains
                .httpStrictTransportSecurity(hsts -> hsts
                        .includeSubDomains(true)
                        .maxAgeInSeconds(31536000));

        http.headers(headersCustomizer);
    }
}