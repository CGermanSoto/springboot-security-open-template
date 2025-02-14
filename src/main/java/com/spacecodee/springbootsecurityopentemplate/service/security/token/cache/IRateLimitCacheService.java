package com.spacecodee.springbootsecurityopentemplate.service.security.token.cache;

import java.util.Map;

public interface IRateLimitCacheService {

    int incrementAttempts(String key);

    void resetAttempts(String key);

    void cleanupCache();

    Map<String, Integer> getAttemptStats();

}
