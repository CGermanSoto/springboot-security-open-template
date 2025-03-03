package com.spacecodee.springbootsecurityopentemplate.service.security.token.cleanup;

public interface ITokenCleanupService {

    void cleanupAllTokens();

    void cleanupExpiredTokens();

    void cleanupRevokedTokens();

    void cleanupInactiveTokens();

    void cleanupBlacklistedTokens();

}
