package com.spacecodee.springbootsecurityopentemplate.persistence.repository;

import com.spacecodee.ticklyspace.persistence.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModuleRepository extends JpaRepository<ModuleEntity, Integer> {
}