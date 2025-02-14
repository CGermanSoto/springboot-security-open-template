package com.spacecodee.springbootsecurityopentemplate.service.security.token.ratelimit.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.ratelimit.ITokenRateLimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenRateLimitServiceImpl implements ITokenRateLimitService {

    @Value("${security.token.rate-limit.max-attempts}")
    private int maxAttempts;

    @Value("${security.token.rate-limit.window-minutes}")
    private int windowMinutes;

    private final ExceptionShortComponent exceptionComponent;

    private final Cache<String, AtomicInteger> rateLimitCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @Override
    public void checkRateLimit(String key) {
        AtomicInteger attempts = this.rateLimitCache.getIfPresent(key);
        if (attempts != null && attempts.get() >= this.maxAttempts) {
            log.warn("Rate limit exceeded for key: {}", key);
            throw this.exceptionComponent.rateLimitException("token.rate.limit.exceeded",
                    String.valueOf(this.windowMinutes));
        }
    }

    @Override
    public void recordAttempt(String key) {
        AtomicInteger attempts = this.rateLimitCache.getIfPresent(key);
        if (attempts == null) {
            attempts = new AtomicInteger(0);
            this.rateLimitCache.put(key, attempts);
        }
        attempts.incrementAndGet();
    }

    @Override
    public void clearAttempts(String key) {
        this.rateLimitCache.invalidate(key);
    }

}
