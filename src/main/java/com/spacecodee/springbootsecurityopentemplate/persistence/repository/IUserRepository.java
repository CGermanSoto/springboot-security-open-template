package com.spacecodee.springbootsecurityopentemplate.persistence.repository;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("select count(u) from UserEntity u where u.roleEntity.name = ?1")
    long countAdmins(RoleEnum name);

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    List<UserEntity> findByRoleEntity_Name(RoleEnum name);
}