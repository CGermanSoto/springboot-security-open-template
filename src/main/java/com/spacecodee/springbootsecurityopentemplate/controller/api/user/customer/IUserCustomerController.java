package com.spacecodee.springbootsecurityopentemplate.controller.api.user.customer;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.CustomerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerUVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Customer Management", description = "APIs for managing customer users")
@SecurityRequirement(name = "bearerAuth")
public interface IUserCustomerController {

    @Operation(summary = "Create customer", description = "Creates a new customer user")
    @ApiResponse(responseCode = "201", description = "Customer created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid customer data")
    @ApiResponse(responseCode = "409", description = "Username already exists")
    @PostMapping()
    ResponseEntity<ApiResponsePojo> add(
            @Parameter(description = "Customer details")
            @RequestBody @Valid CustomerAVO request,
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);

    @Operation(summary = "Update customer", description = "Updates an existing customer's information")
    @ApiResponse(responseCode = "200", description = "Customer updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid customer data")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "409", description = "Username already exists")
    @PutMapping("/{id}")
    ResponseEntity<ApiResponsePojo> update(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Customer ID")
            @PathVariable int id,
            @Parameter(description = "Customer details")
            @RequestBody @Valid CustomerUVO request);

    @Operation(summary = "Delete customer", description = "Deletes a customer user")
    @ApiResponse(responseCode = "200", description = "Customer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> delete(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Customer ID")
            @PathVariable int id);

    @Operation(summary = "Find customer by ID", description = "Retrieves customer details by ID")
    @ApiResponse(responseCode = "200", description = "Customer found successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<CustomerDTO>> findById(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @Parameter(description = "Customer ID")
            @PathVariable int id);

    @Operation(summary = "List all customers", description = "Retrieves a list of all customers")
    @ApiResponse(responseCode = "200", description = "Customer list retrieved successfully")
    @GetMapping()
    ResponseEntity<ApiResponseDataPojo<List<CustomerDTO>>> findAll(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);
}