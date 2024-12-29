package com.spacecodee.springbootsecurityopentemplate.persistence.repository;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {

    @Query("select r from RoleEntity r where r.name = :defaultRoleName")
    Optional<RoleEntity> findByName(@Param("defaultRoleName") RoleEnum defaultRoleName);

    // get the admin role from properties located in resources/application.properties
    @Query("select r from RoleEntity r where r.name = :roleName")
    Optional<RoleEntity> findAdminRole(@Param("roleName") RoleEnum roleName);
}