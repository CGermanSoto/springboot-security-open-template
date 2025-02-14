package com.spacecodee.springbootsecurityopentemplate.service.core.permission;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.CreatePermissionVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.PermissionFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.UpdatePermissionVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPermissionService {

    PermissionDTO createPermission(CreatePermissionVO createPermissionVO);

    PermissionDTO updatePermission(UpdatePermissionVO updatePermissionVO);

    PermissionDTO getPermissionById(Integer id);

    PermissionDetailDTO getPermissionDetailById(Integer id);

    Page<PermissionDTO> searchPermissions(PermissionFilterVO filterVO);

    List<PermissionDTO> getAllPermissionsByRoleId(Integer roleId);

    List<PermissionDetailDTO> getAllPermissionDetailsByRoleId(Integer roleId);

    List<PermissionDTO> getAllPermissions();

    void deletePermission(Integer id);
}
