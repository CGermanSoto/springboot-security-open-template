package com.spacecodee.springbootsecurityopentemplate.persistence.repository.core;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPermissionRepository extends JpaRepository<PermissionEntity, Integer> {

    @Query("SELECT p FROM PermissionEntity p WHERE " +
            "(:roleId IS NULL OR p.roleEntity.id = :roleId) AND " +
            "(:operationId IS NULL OR p.operationEntity.id = :operationId)")
    Page<PermissionEntity> findByFilters(
            @Param("roleId") Integer roleId,
            @Param("operationId") Integer operationId,
            Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PermissionEntity p " +
            "WHERE p.roleEntity.id = :roleId AND p.operationEntity.id = :operationId")
    boolean existsByRoleIdAndOperationId(
            @Param("roleId") Integer roleId,
            @Param("operationId") Integer operationId);

    @Query("SELECT p FROM PermissionEntity p WHERE p.roleEntity.id = :roleId")
    List<PermissionEntity> findAllByRoleId(@Param("roleId") Integer roleId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PermissionEntity p " +
            "WHERE p.roleEntity.id = :roleId AND p.operationEntity.id = :operationId " +
            "AND p.id != :id")
    boolean existsByRoleIdAndOperationIdAndIdNot(
            @Param("roleId") Integer roleId,
            @Param("operationId") Integer operationId,
            @Param("id") Integer id);
}