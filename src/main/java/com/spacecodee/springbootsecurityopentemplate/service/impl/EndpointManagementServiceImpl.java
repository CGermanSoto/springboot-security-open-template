// EndpointManagementServiceImpl.java
package com.spacecodee.springbootsecurityopentemplate.service.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.module.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.operation.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.permission.PermissionVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.IModuleMapper;
import com.spacecodee.springbootsecurityopentemplate.mappers.IOperationMapper;
import com.spacecodee.springbootsecurityopentemplate.mappers.IPermissionMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IModuleRepository;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IOperationRepository;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IPermissionRepository;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IRoleRepository;
import com.spacecodee.springbootsecurityopentemplate.service.IEndpointManagementService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EndpointManagementServiceImpl implements IEndpointManagementService {

    private final IModuleRepository moduleRepository;
    private final IOperationRepository operationRepository;
    private final IPermissionRepository permissionRepository;
    private final IRoleRepository roleRepository;
    private final IModuleMapper moduleMapper;
    private final IOperationMapper operationMapper;
    private final IPermissionMapper permissionMapper;
    private final ExceptionShortComponent exceptionComponent;

    @Override
    @PreAuthorize("hasRole('ROLE_DEVELOPER')")
    @Transactional
    public ModuleDTO createModule(String locale, ModuleVO moduleVO) {
        var moduleEntity = this.moduleMapper.dtoToEntity(moduleVO);
        var savedEntity = this.moduleRepository.save(moduleEntity);
        return this.moduleMapper.toDto(savedEntity);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_DEVELOPER')")
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
    @PreAuthorize("hasRole('ROLE_DEVELOPER')")
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
        return this.permissionMapper.toDto(savedEntity);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_DEVELOPER')")
    @Transactional
    public void removePermission(String locale, Integer permissionId) {
        if (!this.permissionRepository.existsById(permissionId)) {
            throw this.exceptionComponent.permissionNotFoundException("permission.not.found", locale);
        }
        this.permissionRepository.deleteById(permissionId);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_DEVELOPER')")
    @Transactional
    public void removeOperation(String locale, Integer operationId) {
        if (!this.operationRepository.existsById(operationId)) {
            throw this.exceptionComponent.operationNotFoundException("operation.not.found", locale);
        }
        this.operationRepository.deleteById(operationId);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_DEVELOPER')")
    @Transactional
    public void removeModule(String locale, Integer moduleId) {
        if (!this.moduleRepository.existsById(moduleId)) {
            throw this.exceptionComponent.moduleNotFoundException("module.not.found", locale);
        }
        this.moduleRepository.deleteById(moduleId);
    }
}