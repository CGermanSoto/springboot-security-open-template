package com.spacecodee.springbootsecurityopentemplate.service.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsRoleDTO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.details.IRoleDetailsMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IRoleRepository;
import com.spacecodee.springbootsecurityopentemplate.service.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;
    private final IRoleDetailsMapper roleDTOMapper;
    @Value("${security.default.role}")
    private String defaultRoleName;
    @Value("${security.default.roles}")
    private String adminRole;
    private final ExceptionShortComponent exceptionShortComponent;

    public RoleServiceImpl(IRoleRepository roleRepository, IRoleDetailsMapper roleDTOMapper, ExceptionShortComponent exceptionShortComponent) {
        this.roleRepository = roleRepository;
        this.roleDTOMapper = roleDTOMapper;
        this.exceptionShortComponent = exceptionShortComponent;
    }

    @Override
    public Optional<UserDetailsRoleDTO> findDefaultRole(String lang) {
        var roleEnum = RoleEnum.valueOf(defaultRoleName);
        return this.roleRepository.findByName(roleEnum)
                .map(this.roleDTOMapper::toUserDetailsRoleDTO)
                .or(Optional::empty);
    }

    @Override
    public RoleEntity findAdminRole(String locale) {
        var adminRoleEnum = AppUtils.getRoleEnum(adminRole);
        return this.roleRepository.findAdminRole(adminRoleEnum)
                .orElseThrow(() -> this.exceptionShortComponent.roleNotFoundException("role.not.exists", locale));
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
}
