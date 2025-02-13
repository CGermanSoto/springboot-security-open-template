package com.spacecodee.springbootsecurityopentemplate.controller.api.user.manager.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.manager.IManagerController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager.ManagerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager.ManagerDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.CreateManagerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.ManagerFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.UpdateManagerVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.user.manager.IManagerService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/manager")
public class ManagerControllerImpl extends AbstractController implements IManagerController {

    private final IManagerService managerService;

    public ManagerControllerImpl(MessageParameterHandler messageParameterHandler, IManagerService managerService) {
        super(messageParameterHandler);
        this.managerService = managerService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ManagerDTO>> createManager(CreateManagerVO request) {
        ManagerDTO createdManager = this.managerService.createManager(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        createdManager,
                        "manager.create.success",
                        HttpStatus.CREATED,
                        createdManager.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ManagerDTO>> updateManager(Integer id, @NotNull UpdateManagerVO request) {
        request.setId(id);
        ManagerDTO updatedManager = this.managerService.updateManager(request);
        return ResponseEntity.ok(super.createDataResponse(
                updatedManager,
                "manager.update.success",
                HttpStatus.OK,
                updatedManager.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ManagerDTO>> getManagerById(Integer id) {
        ManagerDTO managerDTO = this.managerService.getManagerById(id);
        return ResponseEntity.ok(super.createDataResponse(
                managerDTO,
                "manager.found.success",
                HttpStatus.OK,
                managerDTO.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ManagerDetailDTO>> getManagerDetailById(Integer id) {
        ManagerDetailDTO managerDetail = this.managerService.getManagerDetailById(id);
        return ResponseEntity.ok(super.createDataResponse(
                managerDetail,
                "manager.detail.found.success",
                HttpStatus.OK,
                managerDetail.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Page<ManagerDTO>>> searchManagers(ManagerFilterVO filterVO) {
        Page<ManagerDTO> managersPage = this.managerService.searchManagers(filterVO);
        return ResponseEntity.ok(super.createDataResponse(
                managersPage,
                "manager.search.success",
                HttpStatus.OK,
                managersPage.getTotalElements()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ManagerDTO>> changeManagerStatus(Integer id, Boolean status) {
        ManagerDTO updatedManager = this.managerService.changeManagerStatus(id, status);
        return ResponseEntity.ok(super.createDataResponse(
                updatedManager,
                "manager.status.change.success",
                HttpStatus.OK,
                updatedManager.getUsername(),
                status));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> deleteManager(Integer id) {
        this.managerService.deleteManager(id);
        return ResponseEntity.ok(super.createResponse(
                "manager.delete.success",
                HttpStatus.OK,
                id.toString()));
    }
    
}
