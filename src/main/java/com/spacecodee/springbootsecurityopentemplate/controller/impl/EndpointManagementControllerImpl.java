package com.spacecodee.springbootsecurityopentemplate.controller.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.IEndpointManagementController;
import com.spacecodee.springbootsecurityopentemplate.data.dto.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.module.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.operation.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.permission.PermissionVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.IEndpointManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endpoint-management")
@AllArgsConstructor
public class EndpointManagementControllerImpl implements IEndpointManagementController {

    private final IEndpointManagementService endpointManagementService;
    private final MessageUtilComponent messageUtilComponent;

    @Override
    public ResponseEntity<ApiResponseDataPojo<ModuleDTO>> createModule(String locale, ModuleVO moduleVO) {
        var apiResponse = new ApiResponseDataPojo<ModuleDTO>();
        apiResponse.setData(this.endpointManagementService.createModule(locale, moduleVO));
        apiResponse.setMessage(this.messageUtilComponent.getMessage("module.created.success", locale));
        apiResponse.setHttpStatus(HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<OperationDTO>> createOperation(String locale, OperationVO operationVO) {
        var apiResponse = new ApiResponseDataPojo<OperationDTO>();
        apiResponse.setData(this.endpointManagementService.createOperation(locale, operationVO));
        apiResponse.setMessage(this.messageUtilComponent.getMessage("operation.created.success", locale));
        apiResponse.setHttpStatus(HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<PermissionDTO>> assignPermission(String locale,
                                                                               PermissionVO permissionVO) {
        var apiResponse = new ApiResponseDataPojo<PermissionDTO>();
        apiResponse.setData(this.endpointManagementService.assignPermission(locale, permissionVO));
        apiResponse.setMessage(this.messageUtilComponent.getMessage("permission.assigned.success", locale));
        apiResponse.setHttpStatus(HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponsePojo> removePermission(String locale, Integer permissionId) {
        var apiResponse = new ApiResponsePojo();
        this.endpointManagementService.removePermission(locale, permissionId);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("permission.removed.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponsePojo> removeOperation(String locale, Integer operationId) {
        var apiResponse = new ApiResponsePojo();
        this.endpointManagementService.removeOperation(locale, operationId);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("operation.removed.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponsePojo> removeModule(String locale, Integer moduleId) {
        var apiResponse = new ApiResponsePojo();
        this.endpointManagementService.removeModule(locale, moduleId);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("module.removed.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }
}