package com.spacecodee.springbootsecurityopentemplate.persistence.repository;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOperationRepository extends JpaRepository<OperationEntity, Integer> {
    boolean existsByTagAndModuleEntity_Id(String tag, Integer moduleId);

    @Query("SELECT o FROM OperationEntity o WHERE o.permitAll = true")
    List<OperationEntity> findByPermitAllIsPublic();
}