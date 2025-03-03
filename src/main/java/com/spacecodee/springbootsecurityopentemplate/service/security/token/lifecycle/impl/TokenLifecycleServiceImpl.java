package com.spacecodee.springbootsecurityopentemplate.service.security.token.lifecycle.impl;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.language.MessageResolverService;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtTokenSecurityService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.lifecycle.ITokenLifecycleService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.state.ITokenStateService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
public class TokenLifecycleServiceImpl implements ITokenLifecycleService {

    private final IJwtTokenSecurityService tokenSecurityService;
    private final MessageResolverService messageResolver;
    private final ExceptionShortComponent exceptionComponent;
    private final ITokenStateService tokenStateService;
    private ITokenLifecycleService self;

    public TokenLifecycleServiceImpl(IJwtTokenSecurityService tokenSecurityService,
                                     MessageResolverService messageResolver,
                                     ExceptionShortComponent exceptionComponent,
                                     ITokenStateService tokenStateService) {
        this.tokenSecurityService = tokenSecurityService;
        this.messageResolver = messageResolver;
        this.exceptionComponent = exceptionComponent;
        this.tokenStateService = tokenStateService;
    }

    @Autowired
    public void setSelf(@Lazy ITokenLifecycleService self) {
        this.self = self;
    }

    @Override
    @Transactional
    public void initiateToken(String token, String username) {
        try {
            JwtTokenEntity tokenEntity = this.tokenSecurityService.findByToken(token);
            this.updateTokenState(tokenEntity, TokenStateEnum.CREATED);
            log.info("Token initiated for user: {}", username);
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.initiate.error", e.getMessage());
            log.error(errorMessage);
            throw this.exceptionComponent.tokenUnexpectedException(errorMessage);
        }
    }

    @Override
    @Transactional
    public void activateToken(String token) {
        try {
            JwtTokenEntity tokenEntity = this.tokenSecurityService.findByToken(token);
            this.updateTokenState(tokenEntity, TokenStateEnum.ACTIVE);
            log.info("Token activated successfully");
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.activate.error", e.getMessage());
            log.error(errorMessage);
            throw this.exceptionComponent.tokenUnexpectedException(errorMessage);
        }
    }

    @Override
    @Transactional
    public void refreshToken(String oldToken, String newToken) {
        try {
            JwtTokenEntity oldTokenEntity = this.tokenSecurityService.findByToken(oldToken);
            JwtTokenEntity newTokenEntity = this.tokenSecurityService.findByToken(newToken);

            this.updateTokenState(oldTokenEntity, TokenStateEnum.REFRESHED);
            this.updateTokenState(newTokenEntity, TokenStateEnum.ACTIVE);

            log.info("Token refreshed successfully");
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.refresh.error", e.getMessage());
            log.error(errorMessage);
            throw this.exceptionComponent.tokenUnexpectedException(errorMessage);
        }
    }

    @Override
    @Transactional
    public void expireToken(String token) {
        try {
            // First, check if the token has already expired to avoid unnecessary updates
            JwtTokenEntity tokenEntity = this.tokenSecurityService.findByToken(token);

            if (tokenEntity.getState() == TokenStateEnum.EXPIRED) {
                log.debug("Token is already in EXPIRED state, skipping update");
                return;
            }

            log.info("Manually expiring token for user: {}", tokenEntity.getUserEntity().getUsername());

            // Use the self-proxy instead of direct call
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
            JwtTokenEntity tokenEntity = this.tokenSecurityService.findByToken(token);
            this.updateTokenState(tokenEntity, TokenStateEnum.REVOKED);
            log.info("Token revoked successfully");
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.revoke.error", e.getMessage());
            log.error(errorMessage);
            throw this.exceptionComponent.tokenUnexpectedException(errorMessage);
        }
    }

    @Override
    @Transactional
    public TokenStateEnum getTokenState(String token) {
        try {
            JwtTokenEntity tokenEntity = this.tokenSecurityService.findByToken(token);
            return tokenEntity.getState();
        } catch (Exception e) {
            String errorMessage = this.messageResolver.resolveMessage("token.lifecycle.state.error", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException(errorMessage);
        }
    }

    @Override
    @Transactional
    public void handleExpiredToken(String token) {
        try {
            JwtTokenEntity tokenEntity = this.tokenSecurityService.findByToken(token);

            tokenEntity.setState(TokenStateEnum.EXPIRED)
                    .setValid(false)
                    .setLastOperation("Token expired automatically")
                    .setLastAccessAt(Instant.now())
                    .setUsageCount(tokenEntity.getUsageCount() + 1);

            this.tokenSecurityService.updateTokenFromLifecycle(tokenEntity);

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
        this.tokenSecurityService.updateTokenFromLifecycle(tokenEntity);
    }
}
