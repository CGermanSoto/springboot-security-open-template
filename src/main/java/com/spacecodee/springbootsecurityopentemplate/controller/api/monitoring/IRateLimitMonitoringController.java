package com.spacecodee.springbootsecurityopentemplate.controller.api.monitoring;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Tag(name = "Rate Limit Monitoring", description = "APIs for monitoring rate limits")
@SecurityRequirement(name = "bearerAuth")
public interface IRateLimitMonitoringController {

    @Operation(summary = "Get rate limit stats", description = "Retrieves current rate limit statistics")
    @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @GetMapping("/stats")
    ResponseEntity<ApiResponseDataPojo<Map<String, Integer>>> getAttemptStats();

    @Operation(summary = "Reset rate limit", description = "Resets rate limit for specific IP")
    @ApiResponse(responseCode = "200", description = "Rate limit reset successfully")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PostMapping("/reset/{ip}")
    ResponseEntity<ApiResponsePojo> resetAttempts(
            @Parameter(description = "IP address") @PathVariable String ip);

    @Operation(summary = "Clean up rate limits", description = "Cleans up all rate limit data")
    @ApiResponse(responseCode = "200", description = "Cleanup completed successfully")
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            required = true,
            description = "Language code (e.g., 'en' for English, 'es' for Spanish)",
            example = "en",
            schema = @Schema(type = "string", defaultValue = "en")
    )
    @PostMapping("/cleanup")
    ResponseEntity<ApiResponsePojo> cleanupCache();
}
