package com.spacecodee.springbootsecurityopentemplate.controller.api.user.guest;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest.GuestDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest.GuestDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.CreateGuestVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.UpdateGuestVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.GuestFilterVO;
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

@Tag(name = "Guest Management", description = "APIs for managing guests")
@SecurityRequirement(name = "bearerAuth")
public interface IGuestController {

    @Operation(summary = "Create guest", description = "Creates a new guest")
    @ApiResponse(responseCode = "201", description = "Guest created successfully")
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
    ResponseEntity<ApiResponseDataPojo<GuestDTO>> createGuest(
            @Parameter(description = "Guest details") @Valid @RequestBody CreateGuestVO request);

    @Operation(summary = "Update guest", description = "Updates an existing guest")
    @ApiResponse(responseCode = "200", description = "Guest updated successfully")
    @ApiResponse(responseCode = "404", description = "Guest not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PutMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<GuestDTO>> updateGuest(
            @Parameter(description = "Guest ID") @PathVariable Integer id,
            @Parameter(description = "Guest details") @Valid @RequestBody UpdateGuestVO request);

    @Operation(summary = "Get guest", description = "Retrieves guest by ID")
    @ApiResponse(responseCode = "200", description = "Guest found")
    @ApiResponse(responseCode = "404", description = "Guest not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<GuestDTO>> getGuestById(
            @Parameter(description = "Guest ID") @PathVariable Integer id);

    @Operation(summary = "Get guest details", description = "Retrieves detailed guest information by ID")
    @ApiResponse(responseCode = "200", description = "Guest details found")
    @ApiResponse(responseCode = "404", description = "Guest not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/{id}/detail")
    ResponseEntity<ApiResponseDataPojo<GuestDetailDTO>> getGuestDetailById(
            @Parameter(description = "Guest ID") @PathVariable Integer id);

    @Operation(summary = "Search guests", description = "Searches guests with filters")
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
    ResponseEntity<ApiResponseDataPojo<Page<GuestDTO>>> searchGuests(
            @Parameter(description = "Search filters") @Valid GuestFilterVO filterVO);

    @Operation(summary = "Change guest status", description = "Changes the status of a guest")
    @ApiResponse(responseCode = "200", description = "Status changed successfully")
    @ApiResponse(responseCode = "404", description = "Guest not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PatchMapping("/{id}/status/{status}")
    ResponseEntity<ApiResponseDataPojo<GuestDTO>> changeGuestStatus(
            @Parameter(description = "Guest ID") @PathVariable Integer id,
            @Parameter(description = "New status") @PathVariable Boolean status);

    @Operation(summary = "Delete guest", description = "Deletes an existing guest")
    @ApiResponse(responseCode = "200", description = "Guest deleted successfully")
    @ApiResponse(responseCode = "404", description = "Guest not found")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> deleteGuest(
            @Parameter(description = "Guest ID") @PathVariable Integer id);

}
