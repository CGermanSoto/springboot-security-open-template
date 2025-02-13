package com.spacecodee.springbootsecurityopentemplate.controller.api.user.manager;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager.ManagerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager.ManagerDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.CreateManagerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.ManagerFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.UpdateManagerVO;
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

@Tag(name = "Manager Management", description = "APIs for managing managers")
@SecurityRequirement(name = "bearerAuth")
public interface IManagerController {

    @Operation(summary = "Create manager", description = "Creates a new manager")
    @ApiResponse(responseCode = "201", description = "Manager created successfully")
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
    ResponseEntity<ApiResponseDataPojo<ManagerDTO>> createManager(
            @Parameter(description = "Manager details") @Valid @RequestBody CreateManagerVO request);

    @Operation(summary = "Update manager", description = "Updates an existing manager")
    @ApiResponse(responseCode = "200", description = "Manager updated successfully")
    @ApiResponse(responseCode = "404", description = "Manager not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<ManagerDTO>> updateManager(
            @Parameter(description = "Manager ID") @PathVariable Integer id,
            @Parameter(description = "Manager details") @Valid @RequestBody UpdateManagerVO request);

    @Operation(summary = "Get manager", description = "Retrieves manager by ID")
    @ApiResponse(responseCode = "200", description = "Manager found")
    @ApiResponse(responseCode = "404", description = "Manager not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<ManagerDTO>> getManagerById(
            @Parameter(description = "Manager ID") @PathVariable Integer id);

    @Operation(summary = "Get manager details", description = "Retrieves detailed manager information by ID")
    @ApiResponse(responseCode = "200", description = "Manager details found")
    @ApiResponse(responseCode = "404", description = "Manager not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}/detail")
    ResponseEntity<ApiResponseDataPojo<ManagerDetailDTO>> getManagerDetailById(
            @Parameter(description = "Manager ID") @PathVariable Integer id);

    @Operation(summary = "Search managers", description = "Searches managers with filters")
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
    ResponseEntity<ApiResponseDataPojo<Page<ManagerDTO>>> searchManagers(
            @Parameter(description = "Search filters") @Valid ManagerFilterVO filterVO);

    @Operation(summary = "Change manager status", description = "Changes the status of a manager")
    @ApiResponse(responseCode = "200", description = "Status changed successfully")
    @ApiResponse(responseCode = "404", description = "Manager not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PatchMapping("/{id}/status/{status}")
    ResponseEntity<ApiResponseDataPojo<ManagerDTO>> changeManagerStatus(
            @Parameter(description = "Manager ID") @PathVariable Integer id,
            @Parameter(description = "New status") @PathVariable Boolean status);

    @Operation(summary = "Delete manager", description = "Deletes an existing manager")
    @ApiResponse(responseCode = "200", description = "Manager deleted successfully")
    @ApiResponse(responseCode = "404", description = "Manager not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> deleteManager(
            @Parameter(description = "Manager ID") @PathVariable Integer id);

}
