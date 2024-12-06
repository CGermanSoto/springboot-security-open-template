package com.spacecodee.springbootsecurityopentemplate.controller.api.user.technician;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.TechnicianDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianUVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Technician Management", description = "APIs for managing technician users")
@SecurityRequirement(name = "bearerAuth")
public interface IUserTechnicianController {

    @Operation(summary = "Create technician", description = "Creates a new technician user")
    @ApiResponse(responseCode = "201", description = "Technician created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid technician data")
    @ApiResponse(responseCode = "409", description = "Username already exists")
    @PostMapping()
    ResponseEntity<ApiResponsePojo> add(
            @Parameter(description = "Technician details")
            @RequestBody @Valid TechnicianAVO request,
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);

    @Operation(summary = "Update technician", description = "Updates an existing technician's information")
    @ApiResponse(responseCode = "200", description = "Technician updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid technician data")
    @ApiResponse(responseCode = "404", description = "Technician not found")
    @ApiResponse(responseCode = "409", description = "Username already exists")
    @PutMapping("/{id}")
    ResponseEntity<ApiResponsePojo> update(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Technician ID")
            @PathVariable int id,
            @Parameter(description = "Technician details")
            @RequestBody @Valid TechnicianUVO request);

    @Operation(summary = "Delete technician", description = "Deletes a technician. Cannot delete the last technician.")
    @ApiResponse(responseCode = "200", description = "Technician deleted successfully")
    @ApiResponse(responseCode = "404", description = "Technician not found")
    @ApiResponse(responseCode = "409", description = "Cannot delete last technician")
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> delete(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Technician ID")
            @PathVariable int id);

    @Operation(summary = "Find technician by ID", description = "Retrieves technician details by ID")
    @ApiResponse(responseCode = "200", description = "Technician found successfully")
    @ApiResponse(responseCode = "404", description = "Technician not found")
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<TechnicianDTO>> findById(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Technician ID")
            @PathVariable int id);

    @Operation(summary = "List all technicians", description = "Retrieves a list of all technicians")
    @ApiResponse(responseCode = "200", description = "Technician list retrieved successfully")
    @GetMapping()
    ResponseEntity<ApiResponseDataPojo<List<TechnicianDTO>>> findAll(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);
}