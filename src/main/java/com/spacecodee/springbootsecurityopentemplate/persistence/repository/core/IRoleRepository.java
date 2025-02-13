package com.spacecodee.springbootsecurityopentemplate.persistence.repository.core;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {

    @Query("SELECT r FROM RoleEntity r WHERE " +
            "(:name IS NULL OR UPPER(CAST(r.name AS string)) ILIKE %:name%)")
    Page<RoleEntity> findByFilters(
            @Param("name") String name,
            Pageable pageable);

    @Query("SELECT r FROM RoleEntity r WHERE r.name = :name")
    Optional<RoleEntity> findByName(@Param("name") RoleEnum name);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RoleEntity r " +
            "WHERE r.name = :name")
    boolean existsByName(@Param("name") RoleEnum name);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RoleEntity r " +
            "WHERE r.name = :name AND r.id <> :id")
    boolean existsByNameAndIdNot(
            @Param("name") RoleEnum name,
            @Param("id") Integer id);

    // Get admin role from properties
    @Query("SELECT r FROM RoleEntity r WHERE r.name = :roleName")
    Optional<RoleEntity> findAdminRole(@Param("roleName") RoleEnum roleName);
}