package com.spacecodee.springbootsecurityopentemplate.persistence.repository.core;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.ModuleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IModuleRepository extends JpaRepository<ModuleEntity, Integer> {

    @Query("SELECT m FROM ModuleEntity m WHERE " +
            "(:name IS NULL OR m.name ILIKE %:name%) AND " +
            "(:basePath IS NULL OR m.basePath ILIKE %:basePath%)")
    Page<ModuleEntity> findByFilters(
            @Param("name") String name,
            @Param("basePath") String basePath,
            Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Integer id);

    boolean existsByBasePathIgnoreCase(String basePath);

    boolean existsByBasePathIgnoreCaseAndIdNot(String basePath, Integer id);
}