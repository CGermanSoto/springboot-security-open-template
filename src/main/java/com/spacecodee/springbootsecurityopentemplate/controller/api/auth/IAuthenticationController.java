package com.spacecodee.springbootsecurityopentemplate.controller.api.auth;

import com.spacecodee.springbootsecurityopentemplate.data.common.auth.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Tag(name = "Authentication", description = "Authentication management endpoints")
public interface IAuthenticationController {

        @Operation(summary = "Validate JWT token", description = "Validates if the provided JWT token is valid and not expired")
        @ApiResponse(responseCode = "200", description = "Token is valid")
        @ApiResponse(responseCode = "400", description = "Token is missing")
        @ApiResponse(responseCode = "401", description = "Token is invalid or expired")
        @SecurityRequirement(name = "bearerAuth")
        @GetMapping("/validate-token")
        ResponseEntity<ApiResponseDataPojo<Boolean>> validate(
                        @Parameter(description = "Locale for response messages") @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
                        HttpServletRequest request);

        @Operation(summary = "Authenticate user", description = "Authenticates user credentials and returns JWT token")
        @ApiResponse(responseCode = "202", description = "Successfully authenticated")
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
        @ApiResponse(responseCode = "400", description = "Invalid input")
        @PostMapping("/authenticate")
        ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> authenticate(
                        @Parameter(description = "Locale for response messages") @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
                        @Parameter(description = "Login credentials") @RequestBody @Valid LoginVO request);

        @Operation(summary = "Get user profile", description = "Retrieves the profile of the currently authenticated user")
        @ApiResponse(responseCode = "200", description = "Profile retrieved successfully")
        @ApiResponse(responseCode = "401", description = "Not authenticated")
        @SecurityRequirement(name = "bearerAuth")
        @GetMapping("/profile")
        ResponseEntity<ApiResponseDataPojo<UserSecurityDTO>> profile(
                        @Parameter(description = "Locale for response messages") @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);

        @Operation(summary = "Logout user", description = "Invalidates the current user's JWT token")
        @ApiResponse(responseCode = "200", description = "Successfully logged out")
        @ApiResponse(responseCode = "401", description = "Not authenticated")
        @SecurityRequirement(name = "bearerAuth")
        @PostMapping("/logout")
        ResponseEntity<ApiResponsePojo> logout(
                        @Parameter(description = "Locale for response messages") @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
                        HttpServletRequest request);

        @Operation(summary = "Refresh JWT token", description = "Generates a new JWT token using the current valid token")
        @ApiResponse(responseCode = "200", description = "Token refreshed successfully")
        @ApiResponse(responseCode = "401", description = "Current token is invalid or expired")
        @SecurityRequirement(name = "bearerAuth")
        @PutMapping("/refresh-token")
        ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> refreshToken(
                        @Parameter(description = "Locale for response messages") @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
                        HttpServletRequest request);
}
