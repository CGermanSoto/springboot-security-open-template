package com.spacecodee.springbootsecurityopentemplate.persistence.repository.core;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOperationRepository extends JpaRepository<OperationEntity, Integer> {

    @Query("SELECT o FROM OperationEntity o WHERE " +
            "(:tag IS NULL OR o.tag ILIKE %:tag%) AND " +
            "(:path IS NULL OR o.path = :path) AND " +
            "(:httpMethod IS NULL OR o.httpMethod = :httpMethod) AND " +
            "(:permitAll IS NULL OR o.permitAll = :permitAll) AND " +
            "(:moduleId IS NULL OR o.moduleEntity.id = :moduleId)")
    Page<OperationEntity> findByFilters(
            @Param("tag") String tag,
            @Param("path") String path,
            @Param("httpMethod") String httpMethod,
            @Param("permitAll") Boolean permitAll,
            @Param("moduleId") Integer moduleId,
            Pageable pageable);

    boolean existsByTagAndModuleEntity_Id(String tag, Integer moduleId);

    boolean existsByTagAndModuleEntity_IdAndIdNot(String tag, Integer moduleId, Integer id);

    @Query("SELECT o FROM OperationEntity o WHERE o.permitAll = true")
    List<OperationEntity> findByPermitAllIsTrue();

    List<OperationEntity> findByModuleEntity_Id(Integer moduleId);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM OperationEntity o " +
            "WHERE o.path = :path AND o.httpMethod = :httpMethod AND o.moduleEntity.id = :moduleId")
    boolean existsByPathAndHttpMethodAndModuleEntity_Id(
            @Param("path") String path,
            @Param("httpMethod") String httpMethod,
            @Param("moduleId") Integer moduleId);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM OperationEntity o " +
            "WHERE o.tag = :tag")
    boolean existsByTag(@Param("tag") String tag);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM OperationEntity o " +
            "WHERE o.path = :path AND o.httpMethod = :httpMethod")
    boolean existsByPathAndHttpMethod(
            @Param("path") String path,
            @Param("httpMethod") String httpMethod);
}