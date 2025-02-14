package com.spacecodee.springbootsecurityopentemplate.service.security.token.usage.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.jwt.IJwtTokenSecurityRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.usage.ITokenUsageTrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenUsageTrackingServiceImpl implements ITokenUsageTrackingService {

    private final IJwtTokenSecurityRepository jwtTokenRepository;

    private final ExceptionShortComponent exceptionComponent;

    @Override
    @Transactional
    public void trackTokenExpiration(String token) {
        try {
            this.jwtTokenRepository.incrementTokenUsage(
                    token,
                    "Token expired",
                    Instant.now());
            log.info("Tracked token expiration: {}", token);
        } catch (Exception e) {
            log.error("Error tracking token expiration: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.tracking.expire.error");
        }
    }

    @Override
    @Transactional
    public void trackTokenAccess(String token, String operation) {
        try {
            this.jwtTokenRepository.incrementTokenUsage(token, operation, Instant.now());
        } catch (Exception e) {
            log.error("Error tracking token access: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.tracking.access.error");
        }
    }

    @Override
    @Transactional
    public void trackTokenRefresh(String oldToken, String newToken, String username) {
        try {
            this.jwtTokenRepository.trackTokenRefresh(oldToken, newToken, username);
            log.info("Tracked token refresh: {} -> {}", oldToken, newToken);
        } catch (Exception e) {
            log.error("Error tracking token refresh: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.tracking.refresh.error");
        }
    }

    @Override
    public Map<String, Integer> getTokenUsageStatistics(String token) {
        try {
            return this.jwtTokenRepository.getTokenUsageStats(token);
        } catch (Exception e) {
            log.error("Error getting token usage statistics: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.tracking.stats.error");
        }
    }
}
