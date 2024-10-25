package com.spacecodee.springbootsecurityopentemplate.persistence.repository;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}