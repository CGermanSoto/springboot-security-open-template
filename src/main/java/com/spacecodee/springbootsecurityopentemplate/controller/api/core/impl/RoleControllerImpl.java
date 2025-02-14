package com.spacecodee.springbootsecurityopentemplate.controller.api.core.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.core.IRoleController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.role.RoleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.role.RoleDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.CreateRoleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.RoleFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.UpdateRoleVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
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
@RequestMapping("/security/role")
public class RoleControllerImpl extends AbstractController implements IRoleController {

    private final IRoleService roleService;

    public RoleControllerImpl(MessageParameterHandler messageParameterHandler, IRoleService roleService) {
        super(messageParameterHandler);
        this.roleService = roleService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<RoleDTO>> createRole(CreateRoleVO request) {
        RoleDTO createdRole = this.roleService.createRole(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        createdRole,
                        "role.create.success",
                        HttpStatus.CREATED,
                        createdRole.getName()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<RoleDTO>> updateRole(Integer id, @NotNull UpdateRoleVO request) {
        request.setId(id);
        RoleDTO updatedRole = this.roleService.updateRole(request);
        return ResponseEntity.ok(super.createDataResponse(
                updatedRole,
                "role.update.success",
                HttpStatus.OK,
                updatedRole.getName()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<RoleDTO>> getRoleById(Integer id) {
        RoleDTO role = this.roleService.getRoleById(id);
        return ResponseEntity.ok(super.createDataResponse(
                role,
                "role.found.success",
                HttpStatus.OK,
                role.getName()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<RoleDetailDTO>> getRoleDetailById(Integer id) {
        RoleDetailDTO role = this.roleService.getRoleDetailById(id);
        return ResponseEntity.ok(super.createDataResponse(
                role,
                "role.detail.found.success",
                HttpStatus.OK,
                role.getName()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Page<RoleDTO>>> searchRoles(RoleFilterVO filterVO) {
        Page<RoleDTO> roles = this.roleService.searchRoles(filterVO);
        return ResponseEntity.ok(super.createDataResponse(
                roles,
                "role.search.success",
                HttpStatus.OK,
                roles.getTotalElements()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<RoleDTO>>> getAllRoles() {
        List<RoleDTO> roles = this.roleService.getAllRoles();
        return ResponseEntity.ok(super.createDataResponse(
                roles,
                "role.list.success",
                HttpStatus.OK,
                roles.size()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> deleteRole(Integer id) {
        RoleDTO role = this.roleService.getRoleById(id);
        this.roleService.deleteRole(id);
        return ResponseEntity.ok(super.createResponse(
                "role.delete.success",
                HttpStatus.OK,
                role.getName()));
    }
}
