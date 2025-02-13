package com.spacecodee.springbootsecurityopentemplate.controller.api.core;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.role.RoleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.role.RoleDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.CreateRoleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.RoleFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.UpdateRoleVO;
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

@Tag(name = "Role Management", description = "APIs for managing roles")
@SecurityRequirement(name = "bearerAuth")
public interface IRoleController {

    @Operation(summary = "Create role", description = "Creates a new role")
    @ApiResponse(responseCode = "201", description = "Role created successfully")
    @ApiResponse(responseCode = "400", description = """
            Bad Request. Possible causes:
            - Role name is blank or invalid
            - Role name exceeds 20 characters
            - Role name must be uppercase and contain only letters and underscore
            """)
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PostMapping
    ResponseEntity<ApiResponseDataPojo<RoleDTO>> createRole(
            @Parameter(description = "Role details") @Valid @RequestBody CreateRoleVO request
    );

    @Operation(summary = "Update role", description = "Updates an existing role")
    @ApiResponse(responseCode = "200", description = "Role updated successfully")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<RoleDTO>> updateRole(
            @Parameter(description = "Role ID") @PathVariable Integer id,
            @Parameter(description = "Role details") @Valid @RequestBody UpdateRoleVO request
    );

    @Operation(summary = "Get role by ID", description = "Retrieves role by ID")
    @ApiResponse(responseCode = "200", description = "Role found")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<RoleDTO>> getRoleById(
            @Parameter(description = "Role ID") @PathVariable Integer id
    );

    @Operation(summary = "Get role details by ID", description = "Retrieves detailed role information by ID")
    @ApiResponse(responseCode = "200", description = "Role details found")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}/detail")
    ResponseEntity<ApiResponseDataPojo<RoleDetailDTO>> getRoleDetailById(
            @Parameter(description = "Role ID") @PathVariable Integer id
    );

    @Operation(summary = "Search roles", description = "Searches roles with filters")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/search")
    ResponseEntity<ApiResponseDataPojo<Page<RoleDTO>>> searchRoles(
            @Parameter(description = "Search filters") @Valid RoleFilterVO filterVO
    );

    @Operation(summary = "Get all roles", description = "Retrieves all roles")
    @ApiResponse(responseCode = "200", description = "Roles retrieved successfully")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping
    ResponseEntity<ApiResponseDataPojo<List<RoleDTO>>> getAllRoles();

    @Operation(summary = "Delete role", description = "Deletes a role by ID")
    @ApiResponse(responseCode = "200", description = "Role deleted successfully")
    @ApiResponse(responseCode = "404", description = "Role not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> deleteRole(
            @Parameter(description = "Role ID") @PathVariable Integer id
    );
    
}
