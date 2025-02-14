package com.spacecodee.springbootsecurityopentemplate.service.security.token.state.impl;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.jwt.IJwtTokenSecurityRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.event.ITokenEventBusService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.state.ITokenStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenStateServiceImpl implements ITokenStateService {

    private final IJwtTokenSecurityRepository jwtTokenSecurityRepository;

    private final ITokenEventBusService tokenEventBusService;

    private final ExceptionShortComponent exceptionShortComponent;

    private static final String TOKEN_NOT_FOUND = "token.not.found";

    @Override
    @Transactional
    public void updateTokenState(String token, TokenStateEnum state, String reason) {
        try {
            JwtTokenEntity tokenEntity = this.jwtTokenSecurityRepository.findByToken(token)
                    .orElseThrow(() -> this.exceptionShortComponent
                            .tokenNotFoundException(TOKEN_NOT_FOUND));

            var isValid = state != TokenStateEnum.EXPIRED && tokenEntity.isValid();

            tokenEntity.setState(state)
                    .setValid(isValid)
                    .setLastOperation(reason)
                    .setLastAccessAt(Instant.now());

            this.jwtTokenSecurityRepository.save(tokenEntity);
            this.tokenEventBusService.publishTokenStateChange(token, state, reason);
        } catch (DataIntegrityViolationException e) {
            throw this.exceptionShortComponent.tokenUnexpectedException("token.update.failed");
        }
    }

    private void saveToken(JwtTokenEntity tokenEntity) {
        try {
            this.jwtTokenSecurityRepository.save(tokenEntity);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw this.exceptionShortComponent.tokenUnexpectedException("token.update.concurrent",
                    tokenEntity.getUserEntity().getUsername());
        }
    }

    @Override
    @Transactional
    public void updateTokenAccess(String token, String operation) {
        try {
            JwtTokenEntity tokenEntity = this.jwtTokenSecurityRepository.findByToken(token)
                    .orElseThrow(() -> this.exceptionShortComponent
                            .tokenNotFoundException(TOKEN_NOT_FOUND));

            tokenEntity.setLastOperation(operation)
                    .setLastAccessAt(Instant.now())
                    .setUsageCount(tokenEntity.getUsageCount() + 1);

            this.saveToken(tokenEntity);
        } catch (DataIntegrityViolationException e) {
            throw this.exceptionShortComponent.tokenUnexpectedException("token.update.failed");
        }
    }
}