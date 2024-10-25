package com.spacecodee.springbootsecurityopentemplate.persistence.repository;

import com.spacecodee.ticklyspace.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPermissionRepository extends JpaRepository<PermissionEntity, Integer> {
}