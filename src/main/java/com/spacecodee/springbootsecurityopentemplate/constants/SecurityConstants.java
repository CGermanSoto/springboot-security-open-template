package com.spacecodee.springbootsecurityopentemplate.constants;

import java.util.List;

public final class SecurityConstants {

    private static final String[] SWAGGER_PATHS_ARRAY = {
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources",
            "/webjars/swagger-ui"
    };

    private static final String[] ERROR_PATHS_ARRAY = {
            "/error",
            "/api/v1/error"
    };

    public static final List<String> SWAGGER_PATHS = List.of(SecurityConstants.SWAGGER_PATHS_ARRAY);

    public static final List<String> ERROR_PATHS = List.of(SecurityConstants.ERROR_PATHS_ARRAY);

    public static final String REFRESH_TOKEN_PATH = "/auth/refresh-token";

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }
}
