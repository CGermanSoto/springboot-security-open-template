package com.spacecodee.springbootsecurityopentemplate.service.security.token.facade;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import org.springframework.stereotype.Component;

import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtProviderService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.lifecycle.ITokenLifecycleService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenOperationsFacade {

    private final IJwtProviderService jwtProviderService;
    private final ITokenLifecycleService tokenLifecycleService;

    public boolean validateToken(String token) {
        boolean isValid = jwtProviderService.isTokenValid(token);

        if (isValid) {
            this.tokenLifecycleService.handleTokenAccess(token, "Token validated");
        } else {
            this.tokenLifecycleService.handleExpiredToken(token);
        }
        return isValid;
    }

    public void initiateNewToken(String token, String username) {
        this.tokenLifecycleService.initiateToken(token, username);
    }

    public void activateToken(String token) {
        this.tokenLifecycleService.activateToken(token);
    }

    public void performTokenRefresh(String oldToken, String newToken) {
        this.tokenLifecycleService.refreshToken(oldToken, newToken);
    }

    public String extractUsername(String token) {
        return this.jwtProviderService.extractUsername(token);
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        return this.jwtProviderService.extractJwtFromRequest(request);
    }

    public void handleTokenAuthentication(String token) {
        this.tokenLifecycleService.handleTokenAccess(token, "Token authenticated successfully");
    }

    public void handleTokenExpiration(String token, String reason) {
        this.tokenLifecycleService.markTokenAsExpired(token, reason);
    }

    public TokenStateEnum getTokenState(String token) {
        return this.tokenLifecycleService.getTokenState(token);
    }
}