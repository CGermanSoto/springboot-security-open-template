package com.spacecodee.springbootsecurityopentemplate.controller.api.security;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDetailDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Permission Management related to JWT", description = "APIs for managing permissions to access resources using JWT Permissions based on roles")
@SecurityRequirement(name = "bearerAuth")
public interface IPermissionJwtController {

    @Operation(summary = "Get user permissions", description = "Retrieves all permissions for the current user")
    @ApiResponse(responseCode = "200", description = "Permissions retrieved successfully")
    @ApiResponse(responseCode = "404", description = "The user has no permissions")
    @GetMapping("/user/permissions")
    ResponseEntity<ApiResponseDataPojo<List<PermissionDetailDTO>>> getCurrentUserPermissions();

}
