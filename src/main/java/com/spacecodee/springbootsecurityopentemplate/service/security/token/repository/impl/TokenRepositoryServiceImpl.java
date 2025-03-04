package com.spacecodee.springbootsecurityopentemplate.service.security.token.repository.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.jwt.IJwtTokenSecurityRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.repository.ITokenRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenRepositoryServiceImpl implements ITokenRepositoryService {

    private final IJwtTokenSecurityRepository jwtTokenRepository;

    private final ExceptionShortComponent exceptionComponent;

    private ITokenRepositoryService self;

    private static final String TOKEN_NOT_FOUND = "token.not.found";

    @Autowired
    public void setSelf(@Lazy ITokenRepositoryService self) {
        this.self = self;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JwtTokenEntity> findByToken(String token) {
        return this.jwtTokenRepository.findByToken(token);
    }

    @Override
    @Transactional(readOnly = true)
    public JwtTokenEntity findTokenOrThrow(String token) {
        return self.findByToken(token)
                .orElseThrow(() -> this.exceptionComponent.tokenNotFoundException(TOKEN_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updateToken(JwtTokenEntity tokenEntity) {
        try {
            this.jwtTokenRepository.save(tokenEntity);
        } catch (Exception e) {
            log.error("Error updating token: {}", e.getMessage());
            throw this.exceptionComponent.cannotSaveException("token.update.failed");
        }
    }

    @Override
    @Transactional
    public JwtTokenEntity save(JwtTokenEntity tokenEntity) {
        return this.jwtTokenRepository.save(tokenEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JwtTokenEntity> findAllByUsername(String username) {
        return this.jwtTokenRepository.findAllByUserEntity_Username(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JwtTokenEntity> findJwtTokenEntityByUserEntityUsername(String username) {
        return this.jwtTokenRepository.findFirstByUserEntity_UsernameOrderByCreatedAtDesc(username);
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        this.jwtTokenRepository.deleteByToken(token);
    }
}