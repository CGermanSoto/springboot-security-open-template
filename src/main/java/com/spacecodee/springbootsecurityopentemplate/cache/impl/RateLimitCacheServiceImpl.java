package com.spacecodee.springbootsecurityopentemplate.cache.impl;

import com.google.common.cache.Cache;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.cache.IRateLimitCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitCacheServiceImpl implements IRateLimitCacheService {

    private final Cache<String, Integer> rateLimitCache;
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    public int incrementAttempts(String key) {
        try {
            int attempts = this.rateLimitCache.get(key, () -> 0);
            this.rateLimitCache.put(key, attempts + 1);
            return attempts + 1;
        } catch (Exception e) {
            log.error("Error incrementing rate limit attempts: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenUnexpectedException("rate.limit.cache.increment.error");
        }
    }

    @Override
    public void resetAttempts(String key) {
        try {
            this.rateLimitCache.invalidate(key);
            log.debug("Reset attempts for key: {}", key);
        } catch (Exception e) {
            log.error("Error resetting rate limit attempts: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenUnexpectedException("rate.limit.cache.reset.error");
        }
    }

    @Override
    public void cleanupCache() {
        try {
            this.rateLimitCache.cleanUp();
            log.info("Rate limit cache cleanup completed");
        } catch (Exception e) {
            log.error("Error during rate limit cache cleanup: {}", e.getMessage());
        }
    }

    @Override
    public Map<String, Integer> getAttemptStats() {
        return this.rateLimitCache.asMap();
    }

}
