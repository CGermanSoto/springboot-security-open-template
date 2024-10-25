package com.spacecodee.springbootsecurityopentemplate.persistence.repository;

import com.spacecodee.ticklyspace.enums.RoleEnum;
import com.spacecodee.ticklyspace.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {

    @Query("select r from RoleEntity r where r.name = ?1")
    Optional<RoleEntity> findByName(RoleEnum defaultRoleName);

    // get the admin role from properties located in resources/application.properties
    @Query("select r from RoleEntity r where r.name = ?1")
    Optional<RoleEntity> findAdminRole(RoleEnum roleName);
}