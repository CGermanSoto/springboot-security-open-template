package com.spacecodee.springbootsecurityopentemplate.controller.api.user.editor;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor.EditorDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor.EditorDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.CreateEditorVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.EditorFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.UpdateEditorVO;
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

@Tag(name = "Editor Management", description = "APIs for managing editors")
@SecurityRequirement(name = "bearerAuth")
public interface IEditorController {

    @Operation(summary = "Create editor", description = "Creates a new editor")
    @ApiResponse(responseCode = "201", description = "Editor created successfully")
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
    ResponseEntity<ApiResponseDataPojo<EditorDTO>> createEditor(
            @Parameter(description = "Editor details") @Valid @RequestBody CreateEditorVO request);

    @Operation(summary = "Update editor", description = "Updates an existing editor")
    @ApiResponse(responseCode = "200", description = "Editor updated successfully")
    @ApiResponse(responseCode = "404", description = "Editor not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<EditorDTO>> updateEditor(
            @Parameter(description = "Editor ID") @PathVariable Integer id,
            @Parameter(description = "Editor details") @Valid @RequestBody UpdateEditorVO request);

    @Operation(summary = "Get editor", description = "Retrieves editor by ID")
    @ApiResponse(responseCode = "200", description = "Editor found")
    @ApiResponse(responseCode = "404", description = "Editor not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<EditorDTO>> getEditorById(
            @Parameter(description = "Editor ID") @PathVariable Integer id);

    @Operation(summary = "Get editor details", description = "Retrieves detailed editor information by ID")
    @ApiResponse(responseCode = "200", description = "Editor details found")
    @ApiResponse(responseCode = "404", description = "Editor not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}/detail")
    ResponseEntity<ApiResponseDataPojo<EditorDetailDTO>> getEditorDetailById(
            @Parameter(description = "Editor ID") @PathVariable Integer id);

    @Operation(summary = "Search editors", description = "Searches editors with filters")
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
    ResponseEntity<ApiResponseDataPojo<Page<EditorDTO>>> searchEditors(
            @Parameter(description = "Search filters") @Valid EditorFilterVO filterVO);

    @Operation(summary = "Change editor status", description = "Changes the status of an editor to active (true) or inactive (false)")
    @ApiResponse(responseCode = "200", description = "Status changed successfully")
    @ApiResponse(responseCode = "404", description = "Editor not found")
    @ApiResponse(responseCode = "400", description = "Invalid status value - must be true or false")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PatchMapping("/{id}/status/{status}")
    ResponseEntity<ApiResponseDataPojo<EditorDTO>> changeEditorStatus(
            @Parameter(description = "Editor ID") @PathVariable Integer id,
            @Parameter(description = "New status (true/false)") @PathVariable Boolean status);

    @Operation(summary = "Delete editor", description = "Deletes an existing editor")
    @ApiResponse(responseCode = "200", description = "Editor deleted successfully")
    @ApiResponse(responseCode = "404", description = "Editor not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> deleteEditor(
            @Parameter(description = "Editor ID") @PathVariable Integer id);

}
