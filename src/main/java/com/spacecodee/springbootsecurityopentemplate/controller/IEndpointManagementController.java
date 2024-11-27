// IEndpointManagementController.java
package com.spacecodee.springbootsecurityopentemplate.controller;

import com.spacecodee.springbootsecurityopentemplate.data.dto.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.module.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.operation.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.permission.PermissionVO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IEndpointManagementController {
    @PostMapping("/module")
    ResponseEntity<ApiResponseDataPojo<ModuleDTO>> createModule(
            @RequestHeader(name = "Accept-Language", required = false) String locale,
            @Valid @RequestBody ModuleVO moduleVO);

    @PostMapping("/operation")
    ResponseEntity<ApiResponseDataPojo<OperationDTO>> createOperation(
            @RequestHeader(name = "Accept-Language", required = false) String locale,
            @Valid @RequestBody OperationVO operationVO);

    @PostMapping("/permission")
    ResponseEntity<ApiResponseDataPojo<PermissionDTO>> assignPermission(
            @RequestHeader(name = "Accept-Language", required = false) String locale,
            @Valid @RequestBody PermissionVO permissionVO);

    @DeleteMapping("/permission/{id}")
    ResponseEntity<ApiResponsePojo> removePermission(
            @RequestHeader(name = "Accept-Language", required = false) String locale,
            @PathVariable("id") Integer permissionId);

    @DeleteMapping("/operation/{id}")
    ResponseEntity<ApiResponsePojo> removeOperation(
            @RequestHeader(name = "Accept-Language", required = false) String locale,
            @PathVariable("id") Integer operationId);

    @DeleteMapping("/module/{id}")
    ResponseEntity<ApiResponsePojo> removeModule(
            @RequestHeader(name = "Accept-Language", required = false) String locale,
            @PathVariable("id") Integer moduleId);
}