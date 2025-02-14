package com.spacecodee.springbootsecurityopentemplate.service.core.role;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.role.RoleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.role.RoleDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.CreateRoleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.RoleFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.UpdateRoleVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService {

    RoleDTO createRole(CreateRoleVO createRoleVO);

    RoleDTO updateRole(UpdateRoleVO updateRoleVO);

    RoleDTO getRoleById(Integer id);

    RoleDetailDTO getRoleDetailById(Integer id);

    Page<RoleDTO> searchRoles(RoleFilterVO filterVO);

    List<RoleDTO> getAllRoles();

    void deleteRole(Integer id);

    RoleEntity findByName(String roleName);
}
