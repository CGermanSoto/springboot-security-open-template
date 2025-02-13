package com.spacecodee.springbootsecurityopentemplate.controller.api.auth;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.AuthResponseDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication", description = "APIs for authentication management")
public interface IAuthController {

    @Operation(summary = "Login", description = "Authenticates user and returns tokens")
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @PostMapping("/login")
    ResponseEntity<ApiResponseDataPojo<AuthResponseDTO>> login(
            @Parameter(description = "Login credentials") @Valid @RequestBody LoginVO loginVO);

    @Operation(summary = "Refresh token", description = "Refreshes token from Authorization header")
    @ApiResponse(responseCode = "200", description = "Token refreshed successfully")
    @ApiResponse(responseCode = "401", description = "Invalid or expired token")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/refresh-token")
    ResponseEntity<ApiResponseDataPojo<AuthResponseDTO>> refreshToken(HttpServletRequest request);

    @Operation(summary = "Logout", description = "Invalidates current user session")
    @ApiResponse(responseCode = "200", description = "Logged out successfully")
    @ApiResponse(responseCode = "401", description = "Invalid or missing token")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @PostMapping("/logout")
    ResponseEntity<ApiResponsePojo> logout(HttpServletRequest request);

    @Operation(summary = "Validate token", description = "Validates token from Authorization header")
    @ApiResponse(responseCode = "200", description = "Token is valid")
    @ApiResponse(responseCode = "401", description = "Token is invalid")
    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, required = true, description = "Language code (e.g., 'en' for English, 'es' for Spanish)", example = "en", schema = @Schema(type = "string", defaultValue = "en"))
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/validate-token")
    ResponseEntity<ApiResponseDataPojo<Boolean>> validateToken(HttpServletRequest request);

}
