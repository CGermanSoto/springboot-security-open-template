package com.spacecodee.springbootsecurityopentemplate.security.authentication.filter;

import com.google.common.cache.LoadingCache;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RateLimitFilter extends OncePerRequestFilter {
    private final LoadingCache<String, Integer> requestCountsCache;
    private final ExceptionShortComponent exceptionShortComponent;

    private static final int MAX_ATTEMPTS = 5;

    public RateLimitFilter(LoadingCache<String, Integer> requestCountsCache,
                           ExceptionShortComponent exceptionShortComponent) {
        this.requestCountsCache = requestCountsCache;
        this.exceptionShortComponent = exceptionShortComponent;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (this.isLoginRequest(request)) {
            String clientIp = this.getClientIP(request);
            if (this.isMaximumLoginAttemptsExceeded(response, clientIp)) {
                throw this.exceptionShortComponent.rateLimitExceededException(
                        "error.auth.rate.limit.exceeded",
                        LocaleResolverFilter.getCurrentLocale());
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        final var pathMatcher = new AntPathMatcher();
        log.info("Request URI: {}", request.getRequestURI());

        return request != null &&
                request.getMethod().equals("POST") &&
                pathMatcher.match("/api/v1/auth/login", request.getRequestURI());
    }

    private boolean isMaximumLoginAttemptsExceeded(HttpServletResponse response, String clientIp) {
        try {
            int attempts = this.requestCountsCache.get(clientIp);
            this.addRateLimitHeaders(response, attempts);
            if (attempts >= RateLimitFilter.MAX_ATTEMPTS) {
                log.warn("Rate limit exceeded for IP: {}", clientIp);
                return true;
            }
            this.requestCountsCache.put(clientIp, attempts + 1);
            return false;
        } catch (ExecutionException e) {
            log.error("Cache error for IP: {}", clientIp, e);
            return false;
        }
    }

    private void addRateLimitHeaders(@NotNull HttpServletResponse response, int attempts) {
        response.addHeader("X-RateLimit-Limit", String.valueOf(MAX_ATTEMPTS));
        response.addHeader("X-RateLimit-Remaining", String.valueOf(MAX_ATTEMPTS - attempts));
        response.addHeader("X-RateLimit-Reset",
                String.valueOf(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)));
    }

    @Scheduled(fixedRate = 3600000) // Every hour
    public void cleanupCache() {
        requestCountsCache.cleanUp();
    }

    private String getClientIP(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }
}
