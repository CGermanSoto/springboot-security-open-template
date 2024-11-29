package com.spacecodee.springbootsecurityopentemplate.controller.api.enpoint;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.PermissionVO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IEndpointManagementController {
    @PostMapping("/module")
    ResponseEntity<ApiResponseDataPojo<ModuleDTO>> createModule(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Valid @RequestBody ModuleVO moduleVO);

    @PostMapping("/operation")
    ResponseEntity<ApiResponseDataPojo<OperationDTO>> createOperation(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Valid @RequestBody OperationVO operationVO);

    @PostMapping("/permission")
    ResponseEntity<ApiResponseDataPojo<PermissionDTO>> assignPermission(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Valid @RequestBody PermissionVO permissionVO);

    @DeleteMapping("/permission/{id}")
    ResponseEntity<ApiResponsePojo> removePermission(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable("id") Integer permissionId);

    @DeleteMapping("/operation/{id}")
    ResponseEntity<ApiResponsePojo> removeOperation(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable("id") Integer operationId);

    @DeleteMapping("/module/{id}")
    ResponseEntity<ApiResponsePojo> removeModule(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable("id") Integer moduleId);
}