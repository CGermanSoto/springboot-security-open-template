package com.spacecodee.springbootsecurityopentemplate.controller.api.core.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.core.IPermissionController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.CreatePermissionVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.PermissionFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.UpdatePermissionVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.core.permission.IPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/security/permission")
public class PermissionControllerImpl extends AbstractController implements IPermissionController {

    private final IPermissionService permissionService;

    public PermissionControllerImpl(MessageParameterHandler messageParameterHandler, IPermissionService permissionService) {
        super(messageParameterHandler);
        this.permissionService = permissionService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<PermissionDTO>> createPermission(CreatePermissionVO request) {
        PermissionDTO createdPermission = this.permissionService.createPermission(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        createdPermission,
                        "permission.create.success",
                        HttpStatus.CREATED,
                        createdPermission.getOperationId().toString()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<PermissionDTO>> updatePermission(Integer id,
                                                                               @NotNull UpdatePermissionVO request) {
        request.setId(id);
        PermissionDTO updatedPermission = this.permissionService.updatePermission(request);

        return ResponseEntity.ok(super.createDataResponse(
                updatedPermission,
                "permission.update.success",
                HttpStatus.OK,
                updatedPermission.getOperationId().toString()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<PermissionDTO>> getPermissionById(Integer id) {
        PermissionDTO permission = this.permissionService.getPermissionById(id);

        return ResponseEntity.ok(super.createDataResponse(
                permission,
                "permission.found.success",
                HttpStatus.OK,
                permission.getOperationId().toString()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<PermissionDetailDTO>> getPermissionDetailById(Integer id) {
        PermissionDetailDTO permission = this.permissionService.getPermissionDetailById(id);

        return ResponseEntity.ok(super.createDataResponse(
                permission,
                "permission.detail.found.success",
                HttpStatus.OK,
                permission.getOperationTag()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Page<PermissionDTO>>> searchPermissions(PermissionFilterVO filterVO) {
        Page<PermissionDTO> permissions = this.permissionService.searchPermissions(filterVO);

        return ResponseEntity.ok(super.createDataResponse(
                permissions,
                "permission.search.success",
                HttpStatus.OK,
                permissions.getTotalElements()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<PermissionDTO>>> getAllPermissionsByRoleId(Integer roleId) {
        List<PermissionDTO> permissions = this.permissionService.getAllPermissionsByRoleId(roleId);

        return ResponseEntity.ok(super.createDataResponse(
                permissions,
                "permission.role.list.success",
                HttpStatus.OK,
                permissions.size()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<PermissionDTO>>> getAllPermissions() {
        List<PermissionDTO> permissions = this.permissionService.getAllPermissions();

        return ResponseEntity.ok(super.createDataResponse(
                permissions,
                "permission.list.success",
                HttpStatus.OK,
                permissions.size()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> deletePermission(Integer id) {
        PermissionDTO permission = this.permissionService.getPermissionById(id);
        this.permissionService.deletePermission(id);

        return ResponseEntity.ok(super.createResponse(
                "permission.delete.success",
                HttpStatus.OK,
                permission.getOperationId().toString()));
    }

}
