package com.spacecodee.springbootsecurityopentemplate.utils;

import com.spacecodee.springbootsecurityopentemplate.constants.ApiConstants;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.AntPathMatcher;

@Slf4j
public class PathUtils {

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private PathUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static @NotNull String normalizeRequestPath(@NotNull String requestPath) {
        if (requestPath.startsWith(ApiConstants.API_BASE_PATH)) {
            return requestPath.substring(ApiConstants.API_BASE_PATH.length());
        }
        return requestPath;
    }

    public static boolean matchesPattern(@NotNull String pattern, String path) {
        // Remove trailing slashes and normalize paths
        pattern = pattern.replaceAll("/+$", "");
        path = path.replaceAll("/+$", "");

        // Remove /api/v1 prefix if present
        path = path.replace("/api/v1", "");

        // Split into method and path parts
        String[] patternParts = pattern.split(":", 2);
        String[] pathParts = path.split(":", 2);

        if (patternParts.length != 2 || pathParts.length != 2) {
            return false;
        }

        // Check if methods match
        if (!patternParts[0].equalsIgnoreCase(pathParts[0])) {
            return false;
        }

        // Compare paths
        String patternPath = patternParts[1];
        String requestPath = pathParts[1];

        // Direct match
        if (patternPath.equals(requestPath)) {
            log.debug("Direct path match found");
            return true;
        }

        return pathMatcher.match(patternPath, requestPath);
    }
}
