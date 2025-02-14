package com.spacecodee.springbootsecurityopentemplate.controller.api.user.system.admin;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.system.admin.SystemAdminDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.system.admin.SystemAdminDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.CreateSystemAdminVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.SystemAdminFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.UpdateSystemAdminVO;
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

@Tag(name = "System Admin Management", description = "APIs for managing system administrators")
@SecurityRequirement(name = "bearerAuth")
public interface ISystemAdminController {

    @Operation(summary = "Create system admin", description = "Creates a new system administrator")
    @ApiResponse(responseCode = "201", description = "System admin created successfully")
    @ApiResponse(responseCode = "400", description = """
            Bad Request. Possible causes:
            - Username is blank or invalid format
            - Username length not between 6-15 characters
            - Password doesn't meet complexity requirements
            - First name or last name contain invalid characters
            - Invalid email format
            - Invalid phone number format
            - Invalid profile picture URL
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
    ResponseEntity<ApiResponseDataPojo<SystemAdminDTO>> createSystemAdmin(
            @Parameter(description = "System admin details") @Valid @RequestBody CreateSystemAdminVO request);

    @Operation(summary = "Update system admin", description = "Updates an existing system administrator")
    @ApiResponse(responseCode = "200", description = "System admin updated successfully")
    @ApiResponse(responseCode = "404", description = "System admin not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<SystemAdminDTO>> updateSystemAdmin(
            @Parameter(description = "System admin ID") @PathVariable Integer id,
            @Parameter(description = "System admin details") @Valid @RequestBody UpdateSystemAdminVO request);

    @Operation(summary = "Get system admin", description = "Retrieves system administrator by ID")
    @ApiResponse(responseCode = "200", description = "System admin found")
    @ApiResponse(responseCode = "404", description = "System admin not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<SystemAdminDTO>> getSystemAdminById(
            @Parameter(description = "System admin ID") @PathVariable Integer id);

    @Operation(summary = "Get system admin details", description = "Retrieves detailed system administrator information by ID")
    @ApiResponse(responseCode = "200", description = "System admin details found")
    @ApiResponse(responseCode = "404", description = "System admin not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}/detail")
    ResponseEntity<ApiResponseDataPojo<SystemAdminDetailDTO>> getSystemAdminDetailById(
            @Parameter(description = "System admin ID") @PathVariable Integer id);

    @Operation(summary = "Search system admins", description = "Searches system administrators with filters")
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
    ResponseEntity<ApiResponseDataPojo<Page<SystemAdminDTO>>> searchSystemAdmins(
            @Parameter(description = "Search filters") @Valid SystemAdminFilterVO filterVO);

    @Operation(summary = "Change system admin status", description = "Changes the status of a system administrator")
    @ApiResponse(responseCode = "200", description = "Status changed successfully")
    @ApiResponse(responseCode = "404", description = "System admin not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PatchMapping("/{id}/status/{status}")
    ResponseEntity<ApiResponseDataPojo<SystemAdminDTO>> changeSystemAdminStatus(
            @Parameter(description = "System admin ID") @PathVariable Integer id,
            @Parameter(description = "New status") @PathVariable Boolean status);

    @Operation(summary = "Delete system admin", description = "Deletes an existing system administrator")
    @ApiResponse(responseCode = "200", description = "System admin deleted successfully")
    @ApiResponse(responseCode = "404", description = "System admin not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> deleteSystemAdmin(
            @Parameter(description = "System admin ID") @PathVariable Integer id);

}
