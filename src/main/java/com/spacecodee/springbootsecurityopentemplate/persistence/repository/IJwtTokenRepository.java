package com.spacecodee.springbootsecurityopentemplate.persistence.repository;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IJwtTokenRepository extends JpaRepository<JwtTokenEntity, Integer> {
    Optional<JwtTokenEntity> findByUserEntity_Username(String username);

    Optional<JwtTokenEntity> findByToken(String token);

    boolean existsByToken(String jwt);

    void deleteByToken(String token);
}