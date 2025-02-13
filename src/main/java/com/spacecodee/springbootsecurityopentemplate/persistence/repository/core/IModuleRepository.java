package com.spacecodee.springbootsecurityopentemplate.persistence.repository.core;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModuleRepository extends JpaRepository<ModuleEntity, Integer> {
    boolean existsByName(String name);

    boolean existsByBasePath(String basePath);
}