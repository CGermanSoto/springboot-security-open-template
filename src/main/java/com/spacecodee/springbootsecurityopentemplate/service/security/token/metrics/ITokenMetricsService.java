package com.spacecodee.springbootsecurityopentemplate.service.security.token.metrics;

import java.util.Map;

public interface ITokenMetricsService {

    void recordTokenGeneration(String username);

    void recordTokenValidation(boolean success);

    void recordTokenRefresh(String username);

    void recordTokenInvalidation(String reason);

    Map<String, Long> getTokenMetrics();

}
