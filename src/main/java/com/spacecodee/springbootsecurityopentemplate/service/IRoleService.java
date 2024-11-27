package com.spacecodee.springbootsecurityopentemplate.service;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsRoleDTO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;

import java.util.Optional;

public interface IRoleService {

    Optional<UserDetailsRoleDTO> findDefaultRole(String lang);

    RoleEntity findAdminRole(String locale);

    Optional<RoleEnum> getRoleEnum(String roleName, String lang);

    boolean existsById(int roleId);
}
