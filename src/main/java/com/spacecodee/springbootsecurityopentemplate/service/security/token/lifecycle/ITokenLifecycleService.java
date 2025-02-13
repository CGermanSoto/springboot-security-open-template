package com.spacecodee.springbootsecurityopentemplate.service.security.token.lifecycle;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;

public interface ITokenLifecycleService {

    void initiateToken(String token, String username);

    void activateToken(String token);

    void refreshToken(String oldToken, String newToken);

    void expireToken(String token);

    void revokeToken(String token);

    TokenStateEnum getTokenState(String token);

    void handleExpiredToken(String token);

    void markTokenAsExpired(String token, String reason);

    void handleTokenAccess(String token, String operation);

}
