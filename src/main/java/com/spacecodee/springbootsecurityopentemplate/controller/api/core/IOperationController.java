package com.spacecodee.springbootsecurityopentemplate.controller.api.core;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation.OperationDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.CreateOperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.OperationFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.UpdateOperationVO;
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

@Tag(name = "Operation Management", description = "APIs for managing operations")
@SecurityRequirement(name = "bearerAuth")
public interface IOperationController {

    @Operation(summary = "Create operation", description = "Creates a new operation")
    @ApiResponse(responseCode = "201", description = "Operation created successfully")
    @ApiResponse(responseCode = "400", description = """
            Bad Request. Possible causes:
            - Tag is blank or invalid
            - Tag exceeds 100 characters
            - HTTP method is blank or invalid
            - HTTP method must be one of: GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS
            - HTTP method exceeds 10 characters
            - Module ID is null or invalid
            - Path exceeds 50 characters
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
    ResponseEntity<ApiResponseDataPojo<OperationDTO>> createOperation(
            @Parameter(description = "Operation details") @Valid @RequestBody CreateOperationVO request);

    @Operation(summary = "Update operation", description = "Updates an existing operation")
    @ApiResponse(responseCode = "200", description = "Operation updated successfully")
    @ApiResponse(responseCode = "404", description = "Operation not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<OperationDTO>> updateOperation(
            @Parameter(description = "Operation ID") @PathVariable Integer id,
            @Parameter(description = "Operation details") @Valid @RequestBody UpdateOperationVO request);

    @Operation(summary = "Get operation by ID", description = "Retrieves operation by ID")
    @ApiResponse(responseCode = "200", description = "Operation found")
    @ApiResponse(responseCode = "404", description = "Operation not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<OperationDTO>> getOperationById(
            @Parameter(description = "Operation ID") @PathVariable Integer id);

    @Operation(summary = "Get operation details by ID", description = "Retrieves detailed operation information by ID")
    @ApiResponse(responseCode = "200", description = "Operation details found")
    @ApiResponse(responseCode = "404", description = "Operation not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}/detail")
    ResponseEntity<ApiResponseDataPojo<OperationDetailDTO>> getOperationDetailById(
            @Parameter(description = "Operation ID") @PathVariable Integer id);

    @Operation(summary = "Search operations", description = "Searches operations with filters")
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
    ResponseEntity<ApiResponseDataPojo<Page<OperationDTO>>> searchOperations(
            @Parameter(description = "Search filters") @Valid OperationFilterVO filterVO);

    @Operation(summary = "Get operations by module", description = "Retrieves all operations for a specific module")
    @ApiResponse(responseCode = "200", description = "Operations retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Module not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/module/{moduleId}")
    ResponseEntity<ApiResponseDataPojo<List<OperationDTO>>> getAllOperationsByModuleId(
            @Parameter(description = "Module ID") @PathVariable Integer moduleId);

    @Operation(summary = "Get all operations", description = "Retrieves all operations")
    @ApiResponse(responseCode = "200", description = "Operations retrieved successfully")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping
    ResponseEntity<ApiResponseDataPojo<List<OperationDTO>>> getAllOperations();

    @Operation(summary = "Delete operation", description = "Deletes an operation by ID")
    @ApiResponse(responseCode = "200", description = "Operation deleted successfully")
    @ApiResponse(responseCode = "404", description = "Operation not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> deleteOperation(
            @Parameter(description = "Operation ID") @PathVariable Integer id);

}