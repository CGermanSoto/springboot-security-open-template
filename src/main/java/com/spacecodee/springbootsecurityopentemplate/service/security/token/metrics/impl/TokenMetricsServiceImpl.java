package com.spacecodee.springbootsecurityopentemplate.service.security.token.metrics.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.metrics.ITokenMetricsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenMetricsServiceImpl implements ITokenMetricsService {

    private final ConcurrentMap<String, AtomicLong> metrics = new ConcurrentHashMap<>();
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    public void recordTokenGeneration(String username) {
        try {
            // Increment total token generation counter
            this.incrementMetric("token.generation.count");
            // Track tokens generated per user
            this.incrementMetric("token.generation.user." + username);
        } catch (Exception e) {
            log.error("Error recording token generation metric: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenUnexpectedException("token.metrics.generation.error");
        }
    }

    @Override
    public void recordTokenValidation(boolean success) {

        try {
            // Track total validation attempts
            this.incrementMetric("token.validation.count");
            if (success) {
                // Track successful validations
                this.incrementMetric("token.validation.success");
            } else {
                // Track failed validations
                this.incrementMetric("token.validation.failure");
            }
        } catch (Exception e) {
            log.error("Error recording token validation metric: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenUnexpectedException("token.metrics.validation.error");
        }
    }

    @Override
    public void recordTokenRefresh(String username) {
        try {
            this.incrementMetric("token.refresh.count");
            this.incrementMetric("token.refresh.user." + username);
        } catch (Exception e) {
            log.error("Error recording token refresh metric: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenUnexpectedException("token.metrics.refresh.error");
        }
    }

    @Override
    public void recordTokenInvalidation(String reason) {
        try {
            this.incrementMetric("token.invalidation.count");
            this.incrementMetric("token.invalidation.reason." + reason);
        } catch (Exception e) {
            log.error("Error recording token invalidation metric: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenUnexpectedException("token.metrics.invalidation.error");
        }
    }

    @Override
    public Map<String, Long> getTokenMetrics() {
        return this.metrics.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().get()));
    }

    private void incrementMetric(String key) {
        // computeIfAbsent: Creates new AtomicLong(0) if key doesn't exist
        // incrementAndGet: Atomically increments the counter
        this.metrics.computeIfAbsent(key, k -> new AtomicLong(0)).incrementAndGet();
    }
}
