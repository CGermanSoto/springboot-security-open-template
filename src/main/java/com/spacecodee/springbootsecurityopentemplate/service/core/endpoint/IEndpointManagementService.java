// IEndpointManagementService.java
package com.spacecodee.springbootsecurityopentemplate.service.core.endpoint;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.PermissionVO;

public interface IEndpointManagementService {
    ModuleDTO createModule(String locale, ModuleVO moduleVO);

    OperationDTO createOperation(String locale, OperationVO operationVO);

    PermissionDTO assignPermission(String locale, PermissionVO permissionVO);

    void removePermission(String locale, Integer permissionId);

    void removeOperation(String locale, Integer operationId);

    void removeModule(String locale, Integer moduleId);
}