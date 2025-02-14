package com.spacecodee.springbootsecurityopentemplate.controller.api.core;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.module.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.CreateModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.ModuleFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.UpdateModuleVO;
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

@Tag(name = "Module Management", description = "APIs for managing modules")
@SecurityRequirement(name = "bearerAuth")
public interface IModuleController {

    @Operation(summary = "Create module", description = "Creates a new module")
    @ApiResponse(responseCode = "201", description = "Module created successfully")
    @ApiResponse(responseCode = "400", description = """
            Bad Request. Possible causes:
            - Name is blank or contains special characters
            - Name exceeds 100 characters
            - Base path is blank or invalid format
            - Base path doesn't start with /
            - Base path exceeds 100 characters
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
    ResponseEntity<ApiResponseDataPojo<ModuleDTO>> createModule(
            @Parameter(description = "Module details") @Valid @RequestBody CreateModuleVO request);

    @Operation(summary = "Update module", description = "Updates an existing module")
    @ApiResponse(responseCode = "200", description = "Module updated successfully")
    @ApiResponse(responseCode = "400", description = """
            Bad Request. Possible causes:
            - ID is null or invalid
            - Name is blank or contains special characters
            - Name exceeds 100 characters
            - Base path is blank or invalid format
            - Base path doesn't start with /
            - Base path exceeds 100 characters
            """)
    @ApiResponse(responseCode = "404", description = "Module not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<ModuleDTO>> updateModule(
            @Parameter(description = "Module ID") @PathVariable Integer id,
            @Parameter(description = "Module details") @Valid @RequestBody UpdateModuleVO updateModuleVO);

    @Operation(summary = "Get module by ID", description = "Retrieves module information by ID")
    @ApiResponse(responseCode = "200", description = "Module found")
    @ApiResponse(responseCode = "404", description = "Module not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<ModuleDTO>> getModuleById(
            @Parameter(description = "Module ID") @PathVariable Integer id);

    @Operation(summary = "Search modules", description = "Searches modules with filters")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    @ApiResponse(responseCode = "400", description = """
            Bad Request. Possible causes:
            - Page number is negative
            - Page size is less than 1 or greater than 100
            - Invalid sort field
            - Invalid sort direction (must be ASC or DESC)
            """)
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/search")
    ResponseEntity<ApiResponseDataPojo<Page<ModuleDTO>>> searchModules(
            @Parameter(description = "Search filters") @Valid ModuleFilterVO filterVO);

    @Operation(summary = "Get all modules", description = "Retrieves all modules")
    @ApiResponse(responseCode = "200", description = "Modules retrieved successfully")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping
    ResponseEntity<ApiResponseDataPojo<List<ModuleDTO>>> getAllModules();

    @Operation(summary = "Delete module", description = "Deletes a module by ID")
    @ApiResponse(responseCode = "200", description = "Module deleted successfully")
    @ApiResponse(responseCode = "404", description = "Module not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> deleteModule(
            @Parameter(description = "Module ID") @PathVariable Integer id);

}