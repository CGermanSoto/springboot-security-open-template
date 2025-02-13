package com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.operation;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOperationSecurityRepository extends JpaRepository<OperationEntity, Integer> {

    @Query("SELECT o FROM OperationEntity o JOIN FETCH o.moduleEntity WHERE o.permitAll = true")
    List<OperationEntity> findByPermitAllIsPublic();
}