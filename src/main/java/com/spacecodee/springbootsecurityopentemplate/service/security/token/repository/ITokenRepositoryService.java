package com.spacecodee.springbootsecurityopentemplate.service.security.token.repository;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;

import java.util.List;
import java.util.Optional;

public interface ITokenRepositoryService {

    Optional<JwtTokenEntity> findByToken(String token);

    JwtTokenEntity findTokenOrThrow(String token);

    void updateToken(JwtTokenEntity tokenEntity);

    List<JwtTokenEntity> findAllByUsername(String username);

    JwtTokenEntity save(JwtTokenEntity tokenEntity);

    void deleteByToken(String token);

    Optional<JwtTokenEntity> findJwtTokenEntityByUserEntityUsername(String username);
}