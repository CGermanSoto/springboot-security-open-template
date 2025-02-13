package com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer.DeveloperDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.CreateDeveloperVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.UpdateDeveloperVO;
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

@Tag(name = "Developer Management", description = "APIs for managing developers")
@SecurityRequirement(name = "bearerAuth")
public interface IDeveloperController {

    @Operation(summary = "Create developer", description = "Creates a new developer")
    @ApiResponse(responseCode = "201", description = "Developer created successfully")
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
    ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> createDeveloper(
            @Parameter(description = "Developer details") @Valid @RequestBody CreateDeveloperVO request);

    @Operation(summary = "Update developer", description = "Updates an existing developer")
    @ApiResponse(responseCode = "200", description = "Developer updated successfully")
    @ApiResponse(responseCode = "404", description = "Developer not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> updateDeveloper(
            @Parameter(description = "Developer ID") @PathVariable Integer id,
            @Parameter(description = "Developer details") @Valid @RequestBody UpdateDeveloperVO request);

    @Operation(summary = "Get developer", description = "Retrieves developer by ID")
    @ApiResponse(responseCode = "200", description = "Developer found")
    @ApiResponse(responseCode = "404", description = "Developer not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> getDeveloperById(
            @Parameter(description = "Developer ID") @PathVariable Integer id);

    @Operation(summary = "Get developer details", description = "Retrieves detailed developer information by ID")
    @ApiResponse(responseCode = "200", description = "Developer details found")
    @ApiResponse(responseCode = "404", description = "Developer not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}/detail")
    ResponseEntity<ApiResponseDataPojo<DeveloperDetailDTO>> getDeveloperDetailById(
            @Parameter(description = "Developer ID") @PathVariable Integer id);

    @Operation(summary = "Search developers", description = "Searches developers with filters")
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
    ResponseEntity<ApiResponseDataPojo<Page<DeveloperDTO>>> searchDevelopers(
            @Parameter(description = "Search filters") @Valid DeveloperFilterVO filterVO);

    @Operation(summary = "Change developer status", description = "Changes the status of a developer")
    @ApiResponse(responseCode = "200", description = "Status changed successfully")
    @ApiResponse(responseCode = "404", description = "Developer not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PatchMapping("/{id}/status/{status}")
    ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> changeDeveloperStatus(
            @Parameter(description = "Developer ID") @PathVariable Integer id,
            @Parameter(description = "New status") @PathVariable Boolean status);

    @Operation(summary = "Delete developer", description = "Deletes an existing developer")
    @ApiResponse(responseCode = "200", description = "Developer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Developer not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> deleteDeveloper(
            @Parameter(description = "Developer ID") @PathVariable Integer id);
}
