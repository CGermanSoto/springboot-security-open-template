package com.spacecodee.springbootsecurityopentemplate.controller.api.monitoring;

import com.google.common.cache.CacheStats;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Monitoring", description = "Cache monitoring endpoints")
public interface ICacheMonitoringController {

    @Operation(summary = "Get cache statistics", description = "Retrieves current cache statistics for rate limiting")
    @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/stats")
    ResponseEntity<ApiResponseDataPojo<CacheStats>> getCacheStats(
            @Parameter(description = "Locale for response messages")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);
}