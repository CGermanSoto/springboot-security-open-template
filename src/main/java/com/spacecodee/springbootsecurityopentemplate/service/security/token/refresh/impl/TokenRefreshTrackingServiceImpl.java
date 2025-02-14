package com.spacecodee.springbootsecurityopentemplate.service.security.token.refresh.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.jwt.IJwtTokenSecurityRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.refresh.ITokenRefreshTrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenRefreshTrackingServiceImpl implements ITokenRefreshTrackingService {

    @Value("${security.jwt.max-refresh-count}")
    private int maxRefreshCount;

    private final IJwtTokenSecurityRepository jwtTokenRepository;

    private final ExceptionShortComponent exceptionComponent;

    @Override
    @Transactional
    public void trackRefresh(String oldToken, String newToken, String username) {
        try {
            this.jwtTokenRepository.trackTokenRefresh(oldToken, newToken, username);
            log.info("Token refresh tracked for user: {}", username);
        } catch (Exception e) {
            log.error("Error tracking token refresh: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.refresh.track.failed");
        }
    }

    @Override
    public int getRefreshCount(String token) {
        try {
            return this.jwtTokenRepository.getTokenRefreshCount(token);
        } catch (Exception e) {
            log.error("Error getting refresh count: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.refresh.count.failed");
        }
    }

    @Override
    public boolean hasExceededRefreshLimit(String token) {
        return this.getRefreshCount(token) >= this.maxRefreshCount;
    }
}
