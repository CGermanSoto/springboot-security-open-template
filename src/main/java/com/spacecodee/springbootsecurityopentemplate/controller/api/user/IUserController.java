package com.spacecodee.springbootsecurityopentemplate.controller.api.user;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.UserProfileDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "User Management", description = "APIs for managing users")
@SecurityRequirement(name = "bearerAuth")
public interface IUserController {

    @Operation(summary = "Get user profile", description = "Retrieves user profile by ID")
    @ApiResponse(responseCode = "200", description = "Profile found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @GetMapping("/{id}/profile")
    ResponseEntity<ApiResponseDataPojo<UserProfileDTO>> getUserProfile(
            @Parameter(description = "User ID") @PathVariable Integer id);

}
