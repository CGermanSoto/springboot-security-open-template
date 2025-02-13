package com.spacecodee.springbootsecurityopentemplate.controller.api.core;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.CreatePermissionVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.PermissionFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.UpdatePermissionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Permission Management", description = "APIs for managing permissions")
@SecurityRequirement(name = "bearerAuth")
public interface IPermissionController {

    @Operation(summary = "Create permission", description = "Creates a new permission")
    @ApiResponse(responseCode = "201", description = "Permission created successfully")
    @ApiResponse(responseCode = "400", description = """
            Bad Request. Possible causes:
            - Role ID is null or invalid
            - Operation ID is null or invalid
            """)
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @PostMapping
    ResponseEntity<ApiResponseDataPojo<PermissionDTO>> createPermission(
            @Parameter(description = "Permission details") @Valid @RequestBody CreatePermissionVO request);

    @Operation(summary = "Update permission", description = "Updates an existing permission")
    @ApiResponse(responseCode = "200", description = "Permission updated successfully")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<PermissionDTO>> updatePermission(
            @Parameter(description = "Permission ID") @PathVariable Integer id,
            @Parameter(description = "Permission details") @Valid @RequestBody UpdatePermissionVO request);

    @Operation(summary = "Get permission by ID", description = "Retrieves permission by ID")
    @ApiResponse(responseCode = "200", description = "Permission found")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<PermissionDTO>> getPermissionById(
            @Parameter(description = "Permission ID") @PathVariable Integer id);

    @Operation(summary = "Get permission details by ID", description = "Retrieves detailed permission information by ID")
    @ApiResponse(responseCode = "200", description = "Permission details found")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @GetMapping("/{id}/detail")
    ResponseEntity<ApiResponseDataPojo<PermissionDetailDTO>> getPermissionDetailById(
            @Parameter(description = "Permission ID") @PathVariable Integer id);

    @Operation(summary = "Search permissions", description = "Searches permissions with filters")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @GetMapping("/search")
    ResponseEntity<ApiResponseDataPojo<Page<PermissionDTO>>> searchPermissions(
            @Parameter(description = "Search filters") @Valid PermissionFilterVO filterVO);

    @Operation(summary = "Get permissions by role", description = "Retrieves all permissions for a specific role")
    @ApiResponse(responseCode = "200", description = "Permissions retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @GetMapping("/role/{roleId}")
    ResponseEntity<ApiResponseDataPojo<List<PermissionDTO>>> getAllPermissionsByRoleId(
            @Parameter(description = "Role ID") @PathVariable Integer roleId);

    @Operation(summary = "Get all permissions", description = "Retrieves all permissions")
    @ApiResponse(responseCode = "200", description = "Permissions retrieved successfully")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @GetMapping
    ResponseEntity<ApiResponseDataPojo<List<PermissionDTO>>> getAllPermissions();

    @Operation(summary = "Delete permission", description = "Deletes a permission by ID")
    @ApiResponse(responseCode = "200", description = "Permission deleted successfully")
    @ApiResponse(responseCode = "404", description = "Permission not found")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> deletePermission(
            @Parameter(description = "Permission ID") @PathVariable Integer id);

}