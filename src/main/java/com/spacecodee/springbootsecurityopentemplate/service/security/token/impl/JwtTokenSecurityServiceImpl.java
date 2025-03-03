package com.spacecodee.springbootsecurityopentemplate.service.security.token.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.CreateJwtTokenVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.UpdateJwtTokenVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.security.IJwtTokenSecurityMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.jwt.IJwtTokenSecurityRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtProviderService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtTokenSecurityService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.lifecycle.ITokenLifecycleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenSecurityServiceImpl implements IJwtTokenSecurityService {

    private final IJwtTokenSecurityRepository jwtTokenRepository;

    private final IJwtTokenSecurityMapper jwtTokenMapper;

    private final ExceptionShortComponent exceptionComponent;

    private final IJwtProviderService jwtProviderService;

    private final ITokenLifecycleService tokenLifecycleService;

    private static final String TOKEN_NOT_FOUND = "token.not.found";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTokenToRefresh(UpdateJwtTokenVO token) {
        try {
            this.jwtTokenRepository.save(this.jwtTokenMapper.toEntity(token));
        } catch (ObjectOptimisticLockingFailureException e) {
            log.error("Concurrent modification detected: {}", e.getMessage());
            throw this.exceptionComponent.cannotSaveException("token.update.concurrent");
        } catch (Exception e) {
            log.error("Error updating token: {}", e.getMessage());
            throw this.exceptionComponent.cannotSaveException("token.update.failed");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTokenFromLifecycle(JwtTokenEntity tokenEntity) {
        try {
            this.jwtTokenRepository.save(tokenEntity);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.error("Concurrent modification in entity detected: {}", e.getMessage());
            throw this.exceptionComponent.cannotSaveException("token.update.concurrent");
        } catch (Exception e) {
            log.error("Error updating token Entity: {}", e.getMessage());
            throw this.exceptionComponent.cannotSaveException("token.update.failed");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invalidateToken(String token) {
        try {
            this.jwtTokenRepository.deleteByToken(token);
        } catch (Exception e) {
            log.error("Error invalidating token: {}", e.getMessage());
            throw this.exceptionComponent.tokenNotFoundException("token.not.delete");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public JwtTokenEntity findByToken(String token) {
        try {
            return this.jwtTokenRepository.findByToken(token)
                    .orElseThrow(() -> this.exceptionComponent
                            .tokenNotFoundException(JwtTokenSecurityServiceImpl.TOKEN_NOT_FOUND));
        } catch (JwtTokenNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error finding token: {}", e.getMessage());
            throw this.exceptionComponent.tokenNotFoundException(JwtTokenSecurityServiceImpl.TOKEN_NOT_FOUND);
        }
    }

    @Override
    @Transactional(noRollbackFor = JwtTokenNotFoundException.class)
    public JwtTokenEntity handleExistingToken(String username, boolean includeExpired) {
        JwtTokenEntity existingToken = jwtTokenRepository.findJwtTokenEntityByUserEntity_Username(username)
                .orElse(null);

        if (existingToken == null) {
            return null;
        }

        if (!includeExpired && !existingToken.isValid()) {
            return null;
        }

        boolean isValid = jwtProviderService.isTokenValid(existingToken.getToken());
        if (isValid) {
            return existingToken;
        }

        // Return an expired token if requested
        return includeExpired ? existingToken : null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public JwtTokenEntity createNewTokenInLogin(UserSecurityDTO userSecurityDTO, UserEntity user) {
        Map<String, Object> extraClaims = this.jwtProviderService.generateExtraClaims(userSecurityDTO);
        String newToken = this.jwtProviderService.generateToken(userSecurityDTO, extraClaims);
        Instant expiryDate = this.jwtProviderService.extractExpiration(newToken);

        CreateJwtTokenVO tokenVO = this.jwtTokenMapper.toCreateJwtTokenVO(newToken, user, expiryDate);
        JwtTokenEntity tokenEntity = this.jwtTokenMapper.toEntity(tokenVO);
        return this.jwtTokenRepository.save(tokenEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public JwtTokenEntity refreshExistingTokenOnLogin(UserSecurityDTO userDetails, JwtTokenEntity existingToken) {
        Map<String, Object> extraClaims = this.jwtProviderService.generateExtraClaims(userDetails);
        String newToken = this.jwtProviderService.generateToken(userDetails, extraClaims);
        Instant expiryDate = this.jwtProviderService.extractExpiration(newToken);

        UpdateJwtTokenVO updateVO = this.jwtTokenMapper.toRefreshTokenVO(newToken, expiryDate, existingToken);
        JwtTokenEntity refreshedToken = this.jwtTokenMapper.toEntity(updateVO);
        refreshedToken.setId(existingToken.getId());
        refreshedToken.setUserEntity(existingToken.getUserEntity());

        return this.jwtTokenRepository.save(refreshedToken);
    }

    @Override
    @Transactional
    public int revokeAllUserTokens(String username, String reason) {
        try {
            List<JwtTokenEntity> userTokens = this.jwtTokenRepository
                    .findAllByUserEntity_Username(username);

            if (userTokens == null || userTokens.isEmpty()) {
                return 0;
            }

            log.info("Revoking {} tokens for user {}: {}", userTokens.size(), username, reason);

            int count = 0;
            for (JwtTokenEntity token : userTokens) {
                if (this.revokeToken(token)) {
                    count++;
                }
            }

            return count;
        } catch (Exception e) {
            log.error("Failed to revoke tokens for user {}: {}", username, e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.revoke.all.failed");
        }
    }

    private boolean revokeToken(JwtTokenEntity token) {
        try {
            this.tokenLifecycleService.revokeToken(token.getToken());
            return true;
        } catch (Exception e) {
            log.warn("Could not revoke token {}: {}", token.getId(), e.getMessage());
            return false;
        }
    }

}
