package com.spacecodee.springbootsecurityopentemplate.controller.api.user.admin;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.AdminDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminUVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin Management", description = "APIs for managing administrator users")
@SecurityRequirement(name = "bearerAuth")
public interface IUserAdminController {

    @Operation(summary = "Create admin", description = "Creates a new administrator user")
    @ApiResponse(responseCode = "201", description = "Admin created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid admin data")
    @ApiResponse(responseCode = "409", description = "Username already exists")
    @PostMapping()
    ResponseEntity<ApiResponsePojo> add(
            @Parameter(description = "Admin details")
            @RequestBody @Valid AdminAVO request,
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);

    @Operation(summary = "Update admin", description = "Updates an existing administrator's information")
    @ApiResponse(responseCode = "200", description = "Admin updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid admin data")
    @ApiResponse(responseCode = "404", description = "Admin not found")
    @ApiResponse(responseCode = "409", description = "Username already exists")
    @PutMapping("/{id}")
    ResponseEntity<ApiResponsePojo> update(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Admin ID")
            @PathVariable int id,
            @Parameter(description = "Admin details")
            @RequestBody @Valid AdminUVO request);

    @Operation(summary = "Delete admin", description = "Deletes an administrator. Cannot delete the last admin.")
    @ApiResponse(responseCode = "200", description = "Admin deleted successfully")
    @ApiResponse(responseCode = "404", description = "Admin not found")
    @ApiResponse(responseCode = "409", description = "Cannot delete last admin")
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> delete(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Admin ID")
            @PathVariable int id);

    @Operation(summary = "Find admin by ID", description = "Retrieves administrator details by ID")
    @ApiResponse(responseCode = "200", description = "Admin found successfully")
    @ApiResponse(responseCode = "404", description = "Admin not found")
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<AdminDTO>> findById(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Admin ID")
            @PathVariable int id);

    @Operation(summary = "List all admins", description = "Retrieves a list of all administrators")
    @ApiResponse(responseCode = "200", description = "Admin list retrieved successfully")
    @GetMapping()
    ResponseEntity<ApiResponseDataPojo<List<AdminDTO>>> findAll(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);
}