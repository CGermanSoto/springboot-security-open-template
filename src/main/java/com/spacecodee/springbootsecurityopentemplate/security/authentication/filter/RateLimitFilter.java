package com.spacecodee.springbootsecurityopentemplate.security.authentication.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiErrorPojo;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.cache.IRateLimitCacheService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final IRateLimitCacheService rateLimitCacheService;
    private final MessageUtilComponent messageUtilComponent;
    private final ObjectMapper objectMapper;

    @Value("${security.rate-limit.max-attempts}")
    private int maxAttempts;

    @Value("${security.rate-limit.duration-minutes}")
    private int durationMinutes;

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        return !this.isLoginRequest(request);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String clientIp = this.getClientIP(request);

        try {
            int attempts = this.rateLimitCacheService.incrementAttempts(clientIp);
            this.addRateLimitHeaders(response, attempts);

            if (attempts > this.maxAttempts) {
                this.handleRateLimitExceeded(request, response, clientIp);
                return;
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Rate limit error for IP {}: {}", clientIp, e.getMessage());
            filterChain.doFilter(request, response);
        }
    }

    private void handleRateLimitExceeded(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            String clientIp) throws IOException {

        log.warn("Rate limit exceeded for IP: {}", clientIp);
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");

        String errorMessage = this.messageUtilComponent.getMessage(
                "error.auth.rate.limit.exceeded",
                this.durationMinutes);

        ApiErrorPojo error = ApiErrorPojo.of(
                "RateLimitExceeded",
                errorMessage,
                request.getRequestURI(),
                request.getMethod());

        this.objectMapper.writeValue(response.getWriter(), error);
    }

    private boolean isLoginRequest(@NotNull HttpServletRequest request) {
        final var pathMatcher = new AntPathMatcher();

        return request.getMethod().equals("POST")
                && pathMatcher.match("/api/v1/auth/authenticate", request.getRequestURI());
    }

    private void addRateLimitHeaders(@NotNull HttpServletResponse response, int attempts) {
        response.addHeader("X-RateLimit-Limit", String.valueOf(this.maxAttempts));
        response.addHeader("X-RateLimit-Remaining", String.valueOf(this.maxAttempts - attempts));
        response.addHeader("X-RateLimit-Reset",
                String.valueOf(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(this.durationMinutes)));
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
