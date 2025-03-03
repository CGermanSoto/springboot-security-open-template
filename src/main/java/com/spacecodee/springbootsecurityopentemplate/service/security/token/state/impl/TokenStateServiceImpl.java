package com.spacecodee.springbootsecurityopentemplate.service.security.token.state.impl;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.jwt.IJwtTokenSecurityRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.event.ITokenEventBusService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.state.ITokenStateService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenStateServiceImpl implements ITokenStateService {

    private final IJwtTokenSecurityRepository jwtTokenRepository;
    private final ITokenEventBusService tokenEventBusService;
    private final ExceptionShortComponent exceptionComponent;

    private static final String TOKEN_NOT_FOUND = "token.not.found";
    private static final String TOKEN_UPDATE_FAILED = "token.update.failed";

    @Override
    @Transactional
    public void updateTokenState(String token, TokenStateEnum state, String reason) {
        try {
            JwtTokenEntity tokenEntity = this.findTokenOrThrow(token);
            updateEntityState(tokenEntity, state, reason);
            this.jwtTokenRepository.save(tokenEntity);
            this.tokenEventBusService.publishTokenStateChange(token, state, reason);
        } catch (Exception e) {
            throw this.exceptionComponent.tokenUnexpectedException(TOKEN_UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public void revokeToken(String token, String reason) {
        try {
            JwtTokenEntity tokenEntity = this.findTokenOrThrow(token);
            updateEntityState(tokenEntity, TokenStateEnum.REVOKED, reason);
            tokenEntity.setValid(false);
            this.jwtTokenRepository.save(tokenEntity);
        } catch (Exception e) {
            throw this.exceptionComponent.tokenUnexpectedException(TOKEN_UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public void updateTokenAccess(String token, String operation) {
        try {
            JwtTokenEntity tokenEntity = this.findTokenOrThrow(token);
            tokenEntity.setLastOperation(operation)
                    .setLastAccessAt(Instant.now())
                    .setUsageCount(tokenEntity.getUsageCount() + 1);

            this.saveToken(tokenEntity);
        } catch (DataIntegrityViolationException e) {
            throw this.exceptionComponent.tokenUnexpectedException(TOKEN_UPDATE_FAILED);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TokenStateEnum getTokenState(String token) {
        try {
            JwtTokenEntity tokenEntity = this.findTokenOrThrow(token);
            return tokenEntity.getState();
        } catch (Exception e) {
            throw this.exceptionComponent.tokenNotFoundException(TOKEN_NOT_FOUND);
        }
    }

    private JwtTokenEntity findTokenOrThrow(String token) {
        return jwtTokenRepository.findByToken(token)
                .orElseThrow(() -> this.exceptionComponent.tokenNotFoundException(TOKEN_NOT_FOUND));
    }

    private void updateEntityState(@NotNull JwtTokenEntity entity, TokenStateEnum state, String reason) {
        entity.setState(state)
                .setLastOperation(reason)
                .setLastAccessAt(Instant.now())
                .setUsageCount(entity.getUsageCount() + 1);

        if (state == TokenStateEnum.EXPIRED || state == TokenStateEnum.REVOKED) {
            entity.setValid(false);
        }
    }

    private void saveToken(JwtTokenEntity tokenEntity) {
        try {
            this.jwtTokenRepository.save(tokenEntity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw this.exceptionComponent.tokenUnexpectedException("token.update.concurrent",
                    tokenEntity.getUserEntity().getUsername());
        }
    }
}