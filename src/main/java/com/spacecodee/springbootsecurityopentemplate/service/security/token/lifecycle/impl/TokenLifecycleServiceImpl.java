package com.spacecodee.springbootsecurityopentemplate.service.security.token.lifecycle.impl;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.language.MessageResolverService;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.lifecycle.ITokenLifecycleService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.repository.ITokenRepositoryService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.state.ITokenStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenLifecycleServiceImpl implements ITokenLifecycleService {

    private final ITokenRepositoryService tokenRepositoryService;

    private final MessageResolverService messageResolver;

    private final ExceptionShortComponent exceptionComponent;

    private final ITokenStateService tokenStateService;

    private ITokenLifecycleService self;

    @Autowired
    public void setSelf(@Lazy ITokenLifecycleService self) {
        this.self = self;
    }

    @Override
    @Transactional
    public void initiateToken(String token, String username) {
        try {
            JwtTokenEntity tokenEntity = this.tokenRepositoryService.findTokenOrThrow(token);
            this.updateTokenState(tokenEntity, TokenStateEnum.CREATED);
            log.info("Token initiated for user: {}", username);
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.initiate.error", e.getMessage());
            log.error("Failed to initiate token for user {}: {}", username, errorMessage);
            throw this.exceptionComponent.tokenUnexpectedException(errorMessage);
        }
    }

    @Override
    @Transactional
    public void activateToken(String token) {
        try {
            JwtTokenEntity tokenEntity = this.tokenRepositoryService.findTokenOrThrow(token);
            this.updateTokenState(tokenEntity, TokenStateEnum.ACTIVE);
            log.info("Token activated successfully");
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.activate.error", e.getMessage());
            log.error(errorMessage);
            throw this.exceptionComponent.tokenUnexpectedException(errorMessage);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void refreshToken(String oldToken, String newToken) {
        try {
            // Handle old token - continue even if it fails
            this.updateOldTokenState(oldToken);

            // Handle new token
            this.updateNewTokenState(newToken);

            log.info("Token refresh process completed");
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.refresh.error", e.getMessage());
            log.error(errorMessage);
            // Don't rethrow the exception - allows the parent transaction to continue
        }
    }

    private void updateOldTokenState(String oldToken) {
        try {
            JwtTokenEntity oldTokenEntity = this.tokenRepositoryService.findTokenOrThrow(oldToken);
            this.updateTokenState(oldTokenEntity, TokenStateEnum.REFRESHED);
            log.debug("Old token marked as REFRESHED");
        } catch (Exception e) {
            // Don't fail the whole operation if the old token can't be updated
            log.warn("Could not update old token state: {}. Error: {}",
                    oldToken, e.getMessage());
        }
    }

    private void updateNewTokenState(String newToken) {
        try {
            JwtTokenEntity newTokenEntity = this.tokenRepositoryService.findTokenOrThrow(newToken);
            this.updateTokenState(newTokenEntity, TokenStateEnum.ACTIVE);
            log.debug("New token marked as ACTIVE");
        } catch (Exception e) {
            // Log warning but continue - the token might exist but not be visible in this
            // transaction
            log.warn("Could not update new token state: {}. Error: {}",
                    newToken, e.getMessage());
        }
    }

    @Override
    @Transactional
    public void expireToken(String token) {
        try {
            JwtTokenEntity tokenEntity = this.tokenRepositoryService.findTokenOrThrow(token);

            if (tokenEntity.getState() == TokenStateEnum.EXPIRED) {
                log.debug("Token is already in EXPIRED state, skipping update");
                return;
            }

            log.info("Manually expiring token for user: {}", tokenEntity.getUserEntity().getUsername());

            self.markTokenAsExpired(token, "Manual expiration via lifecycle service");
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.expire.error", e.getMessage());
            log.error(errorMessage);
            throw this.exceptionComponent.tokenUnexpectedException(errorMessage);
        }
    }

    @Override
    @Transactional
    public void revokeToken(String token) {
        try {
            this.tokenStateService.revokeToken(token, "Manual revocation via lifecycle service");
            log.info("Token revoked successfully");
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.revoke.error", e.getMessage());
            log.error("Failed to revoke token: {}", errorMessage);
            throw this.exceptionComponent.tokenUnexpectedException(errorMessage);
        }
    }

    @Override
    @Transactional
    public TokenStateEnum getTokenState(String token) {
        return this.tokenStateService.getTokenState(token);
    }

    @Override
    @Transactional
    public void handleExpiredToken(String token) {
        try {
            JwtTokenEntity tokenEntity = this.tokenRepositoryService.findTokenOrThrow(token);

            tokenEntity.setState(TokenStateEnum.EXPIRED)
                    .setValid(false)
                    .setLastOperation("Token expired automatically")
                    .setLastAccessAt(Instant.now())
                    .setUsageCount(tokenEntity.getUsageCount() + 1);

            this.tokenRepositoryService.updateToken(tokenEntity);

            log.info("Token marked as expired: {}", token);
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.expire.error", e.getMessage());
            log.error(errorMessage);
            throw this.exceptionComponent.tokenUnexpectedException(errorMessage);
        }
    }

    @Override
    @Transactional
    public void markTokenAsExpired(String token, String reason) {
        this.tokenStateService.updateTokenState(token, TokenStateEnum.EXPIRED, reason);
    }

    @Override
    @Transactional
    public void handleTokenAccess(String token, String operation) {
        this.tokenStateService.updateTokenAccess(token, operation);
    }

    private void updateTokenState(@NotNull JwtTokenEntity tokenEntity, @NotNull TokenStateEnum state) {
        tokenEntity.setState(state);
        tokenEntity.setUpdatedAt(Instant.now());
        this.tokenRepositoryService.updateToken(tokenEntity);
    }
}
