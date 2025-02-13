package com.spacecodee.springbootsecurityopentemplate.controller.api.user.system.admin.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.system.admin.ISystemAdminController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.system.admin.SystemAdminDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.system.admin.SystemAdminDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.CreateSystemAdminVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.SystemAdminFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.UpdateSystemAdminVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.user.system.admin.ISystemAdminService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/system-admin")
public class SystemAdminControllerImpl extends AbstractController implements ISystemAdminController {

    private final ISystemAdminService systemAdminService;

    public SystemAdminControllerImpl(MessageParameterHandler messageParameterHandler, ISystemAdminService systemAdminService) {
        super(messageParameterHandler);
        this.systemAdminService = systemAdminService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<SystemAdminDTO>> createSystemAdmin(CreateSystemAdminVO request) {
        SystemAdminDTO createdAdmin = this.systemAdminService.createSystemAdmin(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        createdAdmin,
                        "system.admin.create.success",
                        HttpStatus.CREATED,
                        createdAdmin.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<SystemAdminDTO>> updateSystemAdmin(Integer id,
                                                                                 @NotNull UpdateSystemAdminVO request) {
        request.setId(id);
        SystemAdminDTO updatedAdmin = this.systemAdminService.updateSystemAdmin(request);
        return ResponseEntity.ok(super.createDataResponse(
                updatedAdmin,
                "system.admin.update.success",
                HttpStatus.OK,
                updatedAdmin.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<SystemAdminDTO>> getSystemAdminById(Integer id) {
        SystemAdminDTO admin = this.systemAdminService.getSystemAdminById(id);
        return ResponseEntity.ok(super.createDataResponse(
                admin,
                "system.admin.found.success",
                HttpStatus.OK,
                admin.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<SystemAdminDetailDTO>> getSystemAdminDetailById(Integer id) {
        SystemAdminDetailDTO adminDetail = this.systemAdminService.getSystemAdminDetailById(id);
        return ResponseEntity.ok(super.createDataResponse(
                adminDetail,
                "system.admin.detail.found.success",
                HttpStatus.OK,
                adminDetail.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Page<SystemAdminDTO>>> searchSystemAdmins(SystemAdminFilterVO filterVO) {
        Page<SystemAdminDTO> systemAdmins = this.systemAdminService.searchSystemAdmins(filterVO);
        return ResponseEntity.ok(super.createDataResponse(
                systemAdmins,
                "system.admin.search.success",
                HttpStatus.OK,
                systemAdmins.getTotalElements()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<SystemAdminDTO>> changeSystemAdminStatus(Integer id, Boolean status) {
        SystemAdminDTO updatedAdmin = this.systemAdminService.changeSystemAdminStatus(id, status);
        return ResponseEntity.ok(super.createDataResponse(
                updatedAdmin,
                "system.admin.status.change.success",
                HttpStatus.OK,
                updatedAdmin.getUsername(),
                status));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> deleteSystemAdmin(Integer id) {
        this.systemAdminService.deleteSystemAdmin(id);
        return ResponseEntity.ok(super.createResponse(
                "system.admin.delete.success",
                HttpStatus.OK,
                id.toString()));
    }
}
