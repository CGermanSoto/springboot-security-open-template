package com.spacecodee.springbootsecurityopentemplate.service.security.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.jwt.JwtTokenUVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.TokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IJwtTokenMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IJwtTokenRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenManagementService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class JwtTokenManagementServiceImpl implements IJwtTokenManagementService {

    private final IJwtTokenRepository jwtTokenRepository;
    private final IJwtTokenMapper jwtTokenMapper;
    private final ExceptionShortComponent exceptionComponent;

    @Override
    @Transactional
    public void saveToken(JwtTokenUVO token) {
        try {
            this.jwtTokenRepository.save(this.jwtTokenMapper.voToEntity(token));
        } catch (Exception e) {
            log.error("Error saving token: {}", e.getMessage());
            throw this.exceptionComponent.cannotSaveException("token.save.failed", "en");
        }
    }

    @Override
    @Transactional
    public void invalidateToken(String locale, String token) {
        try {
            this.jwtTokenRepository.deleteByToken(token);
        } catch (Exception e) {
            log.error("Error invalidating token: {}", e.getMessage());
            throw this.exceptionComponent.tokenNotFoundException("token.not.delete", locale);
        }
    }

    @Override
    @Transactional
    public void invalidateUserTokens(String locale, Integer userId) {
        try {
            this.jwtTokenRepository.deleteByUserEntityId(userId);
        } catch (Exception e) {
            log.error("Error invalidating user tokens: {}", e.getMessage());
            throw this.exceptionComponent.tokenNotFoundException("token.not.delete", locale);
        }
    }

    @Override
    public String findActiveTokenByUsername(String username) {
        return this.jwtTokenRepository.findByUserEntity_Username(username)
                .map(JwtTokenEntity::getToken)
                .orElse("");
    }

    @Override
    public boolean existsToken(String locale, String token) {
        try {
            return this.jwtTokenRepository.existsByToken(token);
        } catch (TokenExpiredException e) {
            log.error("Error checking token existence: {}", e.getMessage());
            throw this.exceptionComponent.tokenNotFoundException("token.not.exists", locale);
        } catch (Exception e) {
            log.error("Ups! something unexpected happened: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("token.unexpected.error", locale);
        }
    }

    @Override
    public SecurityJwtTokenDTO getTokenDetails(String locale, String token) {
        return this.jwtTokenRepository.findByToken(token)
                .map(this.jwtTokenMapper::toSecurityJwtTokenDTO)
                .orElseThrow(() -> this.exceptionComponent.tokenNotFoundException("token.not.found", locale));
    }
}
