package com.spacecodee.springbootsecurityopentemplate.service.security.token.cleanup;

public interface ITokenCleanupService {

    void cleanupExpiredTokens();

    void cleanupRevokedTokens();

    void cleanupInactiveTokens();

    void cleanupBlacklistedTokens();

}
