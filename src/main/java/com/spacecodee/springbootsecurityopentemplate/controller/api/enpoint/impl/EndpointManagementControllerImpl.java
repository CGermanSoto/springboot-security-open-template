package com.spacecodee.springbootsecurityopentemplate.controller.api.enpoint.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.enpoint.IEndpointManagementController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.PermissionVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.core.endpoint.IEndpointManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endpoint-management")
public class EndpointManagementControllerImpl extends AbstractController implements IEndpointManagementController {
    private final IEndpointManagementService endpointManagementService;

    public EndpointManagementControllerImpl(MessageUtilComponent messageUtilComponent,
                                            IEndpointManagementService endpointManagementService) {
        super(messageUtilComponent);
        this.endpointManagementService = endpointManagementService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ModuleDTO>> createModule(String locale, ModuleVO moduleVO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        this.endpointManagementService.createModule(locale, moduleVO),
                        "module.created.success",
                        locale,
                        HttpStatus.CREATED));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<OperationDTO>> createOperation(String locale, OperationVO operationVO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        this.endpointManagementService.createOperation(locale, operationVO),
                        "operation.created.success",
                        locale,
                        HttpStatus.CREATED));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<PermissionDTO>> assignPermission(String locale,
                                                                               PermissionVO permissionVO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        this.endpointManagementService.assignPermission(locale, permissionVO),
                        "permission.assigned.success",
                        locale,
                        HttpStatus.CREATED));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> removePermission(String locale, Integer permissionId) {
        this.endpointManagementService.removePermission(locale, permissionId);
        return ResponseEntity.ok(super.createResponse(
                "permission.removed.success",
                locale,
                HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> removeOperation(String locale, Integer operationId) {
        this.endpointManagementService.removeOperation(locale, operationId);
        return ResponseEntity.ok(super.createResponse(
                "operation.removed.success",
                locale,
                HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> removeModule(String locale, Integer moduleId) {
        this.endpointManagementService.removeModule(locale, moduleId);
        return ResponseEntity.ok(super.createResponse(
                "module.removed.success",
                locale,
                HttpStatus.OK));
    }
}