package com.spacecodee.springbootsecurityopentemplate.controller.api.monitoring.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.monitoring.IRateLimitMonitoringController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.cache.IRateLimitCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/monitoring/rate-limit")
public class RateLimitMonitoringControllerImpl extends AbstractController implements IRateLimitMonitoringController {

    private final IRateLimitCacheService rateLimitCacheService;

    public RateLimitMonitoringControllerImpl(MessageParameterHandler messageParameterHandler, IRateLimitCacheService rateLimitCacheService) {
        super(messageParameterHandler);
        this.rateLimitCacheService = rateLimitCacheService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Map<String, Integer>>> getAttemptStats() {
        Map<String, Integer> stats = this.rateLimitCacheService.getAttemptStats();
        return ResponseEntity.ok(super.createDataResponse(
                stats,
                "rate.limit.stats.success",
                HttpStatus.OK,
                stats.size()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> resetAttempts(String ip) {
        this.rateLimitCacheService.resetAttempts(ip);
        return ResponseEntity.ok(super.createResponse(
                "rate.limit.reset.success",
                HttpStatus.OK,
                ip));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> cleanupCache() {
        this.rateLimitCacheService.cleanupCache();
        return ResponseEntity.ok(super.createResponse(
                "rate.limit.cleanup.success",
                HttpStatus.OK));
    }
}
