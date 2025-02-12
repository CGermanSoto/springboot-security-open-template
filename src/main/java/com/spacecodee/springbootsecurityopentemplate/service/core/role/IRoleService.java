package com.spacecodee.springbootsecurityopentemplate.service.core.role;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.RoleSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;

import java.util.Optional;

public interface IRoleService {

    Optional<RoleSecurityDTO> findDefaultRole(String lang);

    RoleEntity findAdminRole(String locale);

    Optional<RoleEnum> getRoleEnum(String roleName, String lang);

    boolean existsById(int roleId);

    RoleEntity findByName(String developerRole, String locale);
}
