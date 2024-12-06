package com.spacecodee.springbootsecurityopentemplate.controller.api.enpoint;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.PermissionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Endpoint Management", description = "APIs for managing modules, operations and permissions")
@SecurityRequirement(name = "bearerAuth")
public interface IEndpointManagementController {

    @Operation(summary = "Create module", description = "Creates a new module for endpoint grouping")
    @ApiResponse(responseCode = "201", description = "Module created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid module data")
    @ApiResponse(responseCode = "409", description = "Module already exists")
    @PostMapping("/module")
    ResponseEntity<ApiResponseDataPojo<ModuleDTO>> createModule(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Module details")
            @Valid @RequestBody ModuleVO moduleVO);

    @Operation(summary = "Create operation", description = "Creates a new operation within a module")
    @ApiResponse(responseCode = "201", description = "Operation created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid operation data")
    @ApiResponse(responseCode = "404", description = "Module not found")
    @ApiResponse(responseCode = "409", description = "Operation tag already exists in module")
    @PostMapping("/operation")
    ResponseEntity<ApiResponseDataPojo<OperationDTO>> createOperation(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Operation details")
            @Valid @RequestBody OperationVO operationVO);

    @Operation(summary = "Assign permission", description = "Assigns an operation permission to a role")
    @ApiResponse(responseCode = "201", description = "Permission assigned successfully")
    @ApiResponse(responseCode = "400", description = "Invalid permission data")
    @ApiResponse(responseCode = "404", description = "Role or operation not found")
    @PostMapping("/permission")
    ResponseEntity<ApiResponseDataPojo<PermissionDTO>> assignPermission(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Permission details")
            @Valid @RequestBody PermissionVO permissionVO);

    @Operation(summary = "Remove permission", description = "Removes a permission assignment")
    @ApiResponse(responseCode = "200", description = "Permission removed successfully")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @DeleteMapping("/permission/{id}")
    ResponseEntity<ApiResponsePojo> removePermission(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Permission ID")
            @PathVariable("id") Integer permissionId);

    @Operation(summary = "Remove operation", description = "Removes an operation and its associated permissions")
    @ApiResponse(responseCode = "200", description = "Operation removed successfully")
    @ApiResponse(responseCode = "404", description = "Operation not found")
    @DeleteMapping("/operation/{id}")
    ResponseEntity<ApiResponsePojo> removeOperation(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Operation ID")
            @PathVariable("id") Integer operationId);

    @Operation(summary = "Remove module", description = "Removes a module and all its operations and permissions")
    @ApiResponse(responseCode = "200", description = "Module removed successfully")
    @ApiResponse(responseCode = "404", description = "Module not found")
    @DeleteMapping("/module/{id}")
    ResponseEntity<ApiResponsePojo> removeModule(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Module ID")
            @PathVariable("id") Integer moduleId);
}