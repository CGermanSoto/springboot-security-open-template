package com.spacecodee.springbootsecurityopentemplate.service.security.token.cleanup.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.jwt.IJwtTokenSecurityRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.cleanup.ITokenCleanupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenCleanupServiceImpl implements ITokenCleanupService {

    private final IJwtTokenSecurityRepository jwtTokenRepository;

    private final ExceptionShortComponent exceptionComponent;

    @Override
    @Transactional
    @Scheduled(cron = "${security.token.cleanup.expired.cron}")
    public void cleanupExpiredTokens() {
        this.doCleanupExpiredTokens();
    }

    @Override
    @Transactional
    @Scheduled(cron = "${security.token.cleanup.revoked.cron}")
    public void cleanupRevokedTokens() {
        this.doCleanupRevokedTokens();
    }

    @Override
    @Transactional
    @Scheduled(cron = "${security.token.cleanup.inactive.cron}")
    public void cleanupInactiveTokens() {
        this.doCleanupInactiveTokens();
    }

    @Override
    @Transactional
    @Scheduled(cron = "${security.token.cleanup.blacklisted.cron}")
    public void cleanupBlacklistedTokens() {
        this.doCleanupBlacklistedTokens();
    }

    @Override
    @Transactional
    public void cleanupAllTokens() {
        log.info("Starting comprehensive token cleanup");

        this.doCleanupExpiredTokens();
        this.doCleanupRevokedTokens();
        this.doCleanupInactiveTokens();
        this.doCleanupBlacklistedTokens();

        log.info("Comprehensive token cleanup completed");
    }

    private void doCleanupExpiredTokens() {
        try {
            int count = this.jwtTokenRepository.deleteExpiredTokens(Instant.now());
            log.info("Cleaned up {} expired tokens", count);
        } catch (Exception e) {
            log.error("Error cleaning up expired tokens: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.cleanup.expired.failed");
        }
    }

    private void doCleanupRevokedTokens() {
        try {
            int count = this.jwtTokenRepository.deleteRevokedTokens();
            log.info("Cleaned up {} revoked tokens", count);
        } catch (Exception e) {
            log.error("Error cleaning up revoked tokens: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.cleanup.revoked.failed");
        }
    }

    private void doCleanupInactiveTokens() {
        try {
            Instant threshold = Instant.now().minus(30, ChronoUnit.DAYS);
            int count = this.jwtTokenRepository.deleteInactiveTokens(threshold);
            log.info("Cleaned up {} inactive tokens", count);
        } catch (Exception e) {
            log.error("Error cleaning up inactive tokens: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.cleanup.inactive.failed");
        }
    }

    private void doCleanupBlacklistedTokens() {
        try {
            int count = this.jwtTokenRepository.deleteBlacklistedTokens();
            log.info("Cleaned up {} blacklisted tokens", count);
        } catch (Exception e) {
            log.error("Error cleaning up blacklisted tokens: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.cleanup.blacklisted.failed");
        }
    }
}