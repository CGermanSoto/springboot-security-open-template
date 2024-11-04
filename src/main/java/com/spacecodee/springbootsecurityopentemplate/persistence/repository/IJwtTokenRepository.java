package com.spacecodee.springbootsecurityopentemplate.persistence.repository;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJwtTokenRepository extends JpaRepository<JwtTokenEntity, Integer> {
}