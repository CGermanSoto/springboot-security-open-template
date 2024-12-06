// IUserDeveloperController.java
package com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperUVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Developer Management", description = "APIs for managing developer users")
@SecurityRequirement(name = "bearerAuth")
public interface IUserDeveloperController {

    @Operation(summary = "Create developer", description = "Creates a new developer user")
    @ApiResponse(responseCode = "201", description = "Developer created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid developer data")
    @ApiResponse(responseCode = "409", description = "Username already exists")
    @PostMapping()
    ResponseEntity<ApiResponsePojo> add(
            @Parameter(description = "Developer details")
            @RequestBody @Valid DeveloperAVO request,
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);

    @Operation(summary = "Update developer", description = "Updates an existing developer's information")
    @ApiResponse(responseCode = "200", description = "Developer updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid developer data")
    @ApiResponse(responseCode = "404", description = "Developer not found")
    @ApiResponse(responseCode = "409", description = "Username already exists")
    @PutMapping("/{id}")
    ResponseEntity<ApiResponsePojo> update(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Developer ID")
            @PathVariable int id,
            @Parameter(description = "Developer details")
            @RequestBody @Valid DeveloperUVO request);

    @Operation(summary = "Delete developer", description = "Deletes a developer. Cannot delete the last developer.")
    @ApiResponse(responseCode = "200", description = "Developer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Developer not found")
    @ApiResponse(responseCode = "409", description = "Cannot delete last developer")
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> delete(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Developer ID")
            @PathVariable int id);

    @Operation(summary = "Find developer by ID", description = "Retrieves developer details by ID")
    @ApiResponse(responseCode = "200", description = "Developer found successfully")
    @ApiResponse(responseCode = "404", description = "Developer not found")
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> findById(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Developer ID")
            @PathVariable int id);

    @Operation(summary = "List all developers", description = "Retrieves a list of all developers")
    @ApiResponse(responseCode = "200", description = "Developer list retrieved successfully")
    @GetMapping()
    ResponseEntity<ApiResponseDataPojo<List<DeveloperDTO>>> findAll(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);
}