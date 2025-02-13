package com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer.IDeveloperController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer.DeveloperDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.CreateDeveloperVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.UpdateDeveloperVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.user.developer.IDeveloperService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/security/developer")
public class DeveloperControllerImpl extends AbstractController implements IDeveloperController {

    private final IDeveloperService developerService;

    public DeveloperControllerImpl(MessageParameterHandler messageParameterHandler, IDeveloperService developerService) {
        super(messageParameterHandler);
        this.developerService = developerService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> createDeveloper(CreateDeveloperVO request) {
        DeveloperDTO createdDeveloper = this.developerService.createDeveloper(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        createdDeveloper,
                        "developer.create.success",
                        HttpStatus.CREATED,
                        createdDeveloper.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> updateDeveloper(Integer id, @NotNull UpdateDeveloperVO request) {
        request.setId(id);
        DeveloperDTO updatedDeveloper = this.developerService.updateDeveloper(request);
        return ResponseEntity.ok(super.createDataResponse(
                updatedDeveloper,
                "developer.update.success",
                HttpStatus.OK,
                updatedDeveloper.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> getDeveloperById(Integer id) {
        DeveloperDTO developer = this.developerService.getDeveloperById(id);
        return ResponseEntity.ok(super.createDataResponse(
                developer,
                "developer.found.success",
                HttpStatus.OK,
                developer.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<DeveloperDetailDTO>> getDeveloperDetailById(Integer id) {
        DeveloperDetailDTO developerDetail = this.developerService.getDeveloperDetailById(id);
        return ResponseEntity.ok(super.createDataResponse(
                developerDetail,
                "developer.detail.found.success",
                HttpStatus.OK,
                developerDetail.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Page<DeveloperDTO>>> searchDevelopers(DeveloperFilterVO filterVO) {
        Page<DeveloperDTO> developers = this.developerService.searchDevelopers(filterVO);
        return ResponseEntity.ok(super.createDataResponse(
                developers,
                "developer.search.success",
                HttpStatus.OK,
                developers.getTotalElements()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> changeDeveloperStatus(Integer id, Boolean status) {
        DeveloperDTO updatedDeveloper = this.developerService.changeDeveloperStatus(id, status);
        return ResponseEntity.ok(super.createDataResponse(
                updatedDeveloper,
                "developer.status.change.success",
                HttpStatus.OK,
                updatedDeveloper.getUsername(),
                status));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> deleteDeveloper(Integer id) {
        this.developerService.deleteDeveloper(id);
        return ResponseEntity.ok(super.createResponse(
                "developer.delete.success",
                HttpStatus.OK,
                id.toString()));
    }
}
