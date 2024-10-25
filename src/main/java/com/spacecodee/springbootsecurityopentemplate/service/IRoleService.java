package com.spacecodee.springbootsecurityopentemplate.service;

import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsRoleDTO;
import com.spacecodee.ticklyspace.enums.RoleEnum;
import com.spacecodee.ticklyspace.persistence.entity.RoleEntity;

import java.util.Optional;

public interface IRoleService {

    Optional<UserDetailsRoleDTO> findDefaultRole(String lang);

    Integer findAdminRoleId();

    RoleEntity findAdminRole();

    Optional<RoleEnum> getRoleEnum(String roleName, String lang);

    boolean existsById(int roleId);
}
