package com.spacecodee.springbootsecurityopentemplate.controller.api.user.viewer;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer.ViewerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer.ViewerDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.CreateViewerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.ViewerFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.UpdateViewerVO;
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

@Tag(name = "Viewer Management", description = "APIs for managing viewers")
@SecurityRequirement(name = "bearerAuth")
public interface IViewerController {

    @Operation(summary = "Create viewer", description = "Creates a new viewer")
    @ApiResponse(responseCode = "201", description = "Viewer created successfully")
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
    ResponseEntity<ApiResponseDataPojo<ViewerDTO>> createViewer(
            @Parameter(description = "Viewer details") @Valid @RequestBody CreateViewerVO request);

    @Operation(summary = "Update viewer", description = "Updates an existing viewer")
    @ApiResponse(responseCode = "200", description = "Viewer updated successfully")
    @ApiResponse(responseCode = "404", description = "Viewer not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<ViewerDTO>> updateViewer(
            @Parameter(description = "Viewer ID") @PathVariable Integer id,
            @Parameter(description = "Viewer details") @Valid @RequestBody UpdateViewerVO request);

    @Operation(summary = "Get viewer", description = "Retrieves viewer by ID")
    @ApiResponse(responseCode = "200", description = "Viewer found")
    @ApiResponse(responseCode = "404", description = "Viewer not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<ViewerDTO>> getViewerById(
            @Parameter(description = "Viewer ID") @PathVariable Integer id);

    @Operation(summary = "Get viewer details", description = "Retrieves detailed viewer information by ID")
    @ApiResponse(responseCode = "200", description = "Viewer details found")
    @ApiResponse(responseCode = "404", description = "Viewer not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}/detail")
    ResponseEntity<ApiResponseDataPojo<ViewerDetailDTO>> getViewerDetailById(
            @Parameter(description = "Viewer ID") @PathVariable Integer id);

    @Operation(summary = "Search viewers", description = "Searches viewers with filters")
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
    ResponseEntity<ApiResponseDataPojo<Page<ViewerDTO>>> searchViewers(
            @Parameter(description = "Search filters") @Valid ViewerFilterVO filterVO);

    @Operation(summary = "Change viewer status", description = "Changes the status of a viewer")
    @ApiResponse(responseCode = "200", description = "Status changed successfully")
    @ApiResponse(responseCode = "404", description = "Viewer not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PatchMapping("/{id}/status/{status}")
    ResponseEntity<ApiResponseDataPojo<ViewerDTO>> changeViewerStatus(
            @Parameter(description = "Viewer ID") @PathVariable Integer id,
            @Parameter(description = "New status") @PathVariable Boolean status);

    @Operation(summary = "Delete viewer", description = "Deletes an existing viewer")
    @ApiResponse(responseCode = "200", description = "Viewer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Viewer not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> deleteViewer(
            @Parameter(description = "Viewer ID") @PathVariable Integer id);

}
