package com.spacecodee.springbootsecurityopentemplate.utils;

import com.spacecodee.springbootsecurityopentemplate.constants.ApiConstants;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.AntPathMatcher;

import java.util.Map;
import java.util.regex.Pattern;

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
        pattern = pattern.replaceAll("/+$", "");
        path = path.replaceAll("/+$", "");

        Map<Pattern, String> replacements = Map.of(
                Pattern.compile("/\\[0-9]\\*/"), "/**/", // Numeric wildcards with slash
                Pattern.compile("/\\[0-9\\*]/"), "/**/",
                Pattern.compile("/\\[\\d+\\*]/"), "/**/",
                Pattern.compile("/\\[0-9]\\*$"), "/*", // Numeric wildcards at the end
                Pattern.compile("/\\[0-9\\*]$"), "/*",
                Pattern.compile("/\\[\\d+\\*]$"), "/*",
                Pattern.compile("\\[\\d+]"), "*", // Single numeric segment
                Pattern.compile("\\[\\^/]\\+"), "*", // Any non-slash characters
                Pattern.compile("/\\[\\^/]\\+/"), "/**/", // Any non-slash characters with slash
                Pattern.compile("/\\[\\^/]\\+$"), "/*" // Any non-slash characters at the end
        );

        String antPattern = pattern;
        for (Map.Entry<Pattern, String> replacement : replacements.entrySet()) {
            antPattern = replacement.getKey().matcher(antPattern).replaceAll(replacement.getValue());
        }

        return PathUtils.pathMatcher.match(antPattern, path);
    }
}
