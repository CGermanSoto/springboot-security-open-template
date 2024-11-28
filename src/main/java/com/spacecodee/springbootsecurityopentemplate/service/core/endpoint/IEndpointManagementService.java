// IEndpointManagementService.java
package com.spacecodee.springbootsecurityopentemplate.service.core.endpoint;

import com.spacecodee.springbootsecurityopentemplate.data.dto.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.module.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.operation.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.permission.PermissionVO;

public interface IEndpointManagementService {
    ModuleDTO createModule(String locale, ModuleVO moduleVO);

    OperationDTO createOperation(String locale, OperationVO operationVO);

    PermissionDTO assignPermission(String locale, PermissionVO permissionVO);

    void removePermission(String locale, Integer permissionId);

    void removeOperation(String locale, Integer operationId);

    void removeModule(String locale, Integer moduleId);
}