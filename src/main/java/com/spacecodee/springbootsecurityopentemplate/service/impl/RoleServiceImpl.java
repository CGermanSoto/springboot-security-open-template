package com.spacecodee.springbootsecurityopentemplate.service.impl;

import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsRoleDTO;
import com.spacecodee.ticklyspace.enums.RoleEnum;
import com.spacecodee.ticklyspace.exceptions.RoleNotFoundException;
import com.spacecodee.ticklyspace.mappers.IRoleMapper;
import com.spacecodee.ticklyspace.persistence.entity.RoleEntity;
import com.spacecodee.ticklyspace.persistence.repository.IRoleRepository;
import com.spacecodee.ticklyspace.service.IRoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;
    private final IRoleMapper roleDTOMapper;
    @Value("${security.default.role}")
    private String defaultRoleName;
    @Value("${security.default.roles}")
    private String adminRole;

    public RoleServiceImpl(IRoleRepository roleRepository, IRoleMapper roleDTOMapper) {
        this.roleRepository = roleRepository;
        this.roleDTOMapper = roleDTOMapper;
    }

    @Override
    public Optional<UserDetailsRoleDTO> findDefaultRole(String lang) {
        var roleEnum = RoleEnum.valueOf(defaultRoleName);
        return this.roleRepository.findByName(roleEnum)
                .map(this.roleDTOMapper::toUserDetailsRoleDTO)
                .or(Optional::empty);
    }

    @Override
    public Integer findAdminRoleId() {
        var adminRoleEnum = this.getRoleEnum(adminRole);
        return this.roleRepository.findAdminRole(adminRoleEnum)
                .map(RoleEntity::getId)
                .orElseThrow(() -> new RoleNotFoundException("Admin role not found"));
    }

    @Override
    public RoleEntity findAdminRole() {
        var adminRoleEnum = this.getRoleEnum(adminRole);
        return this.roleRepository.findAdminRole(adminRoleEnum)
                .orElseThrow(() -> new RoleNotFoundException("Admin role not found"));
    }

    @Override
    public Optional<RoleEnum> getRoleEnum(String roleName, String lang) {
        var roleEnum = RoleEnum.valueOf(defaultRoleName);
        return this.roleRepository.findByName(roleEnum)
                .map(roleEntity -> RoleEnum.valueOf(roleEntity.getName().name()))
                .or(Optional::empty);
    }

    @Override
    public boolean existsById(int roleId) {
        return this.roleRepository.existsById(roleId);
    }

    private RoleEnum getRoleEnum(String roleName) {
        return RoleEnum.valueOf(roleName);
    }
}
