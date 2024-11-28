// EndpointManagementServiceImpl.java
package com.spacecodee.springbootsecurityopentemplate.service.core.endpoint.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.module.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.operation.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.permission.PermissionVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.details.IModuleDetailsMapper;
import com.spacecodee.springbootsecurityopentemplate.mappers.details.IOperationDetailsMapper;
import com.spacecodee.springbootsecurityopentemplate.mappers.details.IPermissionDetailsMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IModuleRepository;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IOperationRepository;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IPermissionRepository;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IRoleRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.endpoint.IEndpointManagementService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EndpointManagementServiceImpl implements IEndpointManagementService {

    private final IModuleRepository moduleRepository;
    private final IOperationRepository operationRepository;
    private final IPermissionRepository permissionRepository;
    private final IRoleRepository roleRepository;
    private final IModuleDetailsMapper moduleMapper;
    private final IOperationDetailsMapper operationMapper;
    private final IPermissionDetailsMapper permissionMapper;
    private final ExceptionShortComponent exceptionComponent;

    @Override
    @Transactional
    public ModuleDTO createModule(String locale, ModuleVO moduleVO) {
        var moduleEntity = this.moduleMapper.dtoToEntity(moduleVO);
        var savedEntity = this.moduleRepository.save(moduleEntity);
        return this.moduleMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public OperationDTO createOperation(String locale, @NotNull OperationVO operationVO) {
        var moduleEntity = this.moduleRepository.findById(operationVO.getModuleId())
                .orElseThrow(() -> this.exceptionComponent.moduleNotFoundException("module.not.found", locale));

        var operationEntity = this.operationMapper.voToEntity(operationVO);
        operationEntity.setModuleEntity(moduleEntity);

        var savedEntity = this.operationRepository.save(operationEntity);
        return this.operationMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public PermissionDTO assignPermission(String locale, PermissionVO permissionVO) {
        var roleEntity = this.roleRepository.findById(permissionVO.getRoleId())
                .orElseThrow(() -> this.exceptionComponent.roleNotFoundException("role.not.found", locale));

        var operationEntity = this.operationRepository.findById(permissionVO.getOperationId())
                .orElseThrow(() -> this.exceptionComponent.operationNotFoundException("operation.not.found", locale));

        var permissionEntity = new PermissionEntity()
                .setRoleEntity(roleEntity)
                .setOperationEntity(operationEntity);

        var savedEntity = this.permissionRepository.save(permissionEntity);
        return this.permissionMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public void removePermission(String locale, Integer permissionId) {
        if (!this.permissionRepository.existsById(permissionId)) {
            throw this.exceptionComponent.permissionNotFoundException("permission.not.found", locale);
        }
        this.permissionRepository.deleteById(permissionId);
    }

    @Override
    @Transactional
    public void removeOperation(String locale, Integer operationId) {
        if (!this.operationRepository.existsById(operationId)) {
            throw this.exceptionComponent.operationNotFoundException("operation.not.found", locale);
        }
        this.operationRepository.deleteById(operationId);
    }

    @Override
    @Transactional
    public void removeModule(String locale, Integer moduleId) {
        if (!this.moduleRepository.existsById(moduleId)) {
            throw this.exceptionComponent.moduleNotFoundException("module.not.found", locale);
        }
        this.moduleRepository.deleteById(moduleId);
    }
}