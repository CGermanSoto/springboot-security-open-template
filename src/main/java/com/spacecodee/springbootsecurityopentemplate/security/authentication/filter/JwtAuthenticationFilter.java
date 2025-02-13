package com.spacecodee.springbootsecurityopentemplate.security.authentication.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiErrorPojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenInvalidException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.security.path.ISecurityPathService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.cache.ITokenCacheService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.facade.TokenOperationsFacade;
import com.spacecodee.springbootsecurityopentemplate.service.security.user.IUserSecurityService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenOperationsFacade tokenOperations;

    private final IUserSecurityService userService;

    private final ExceptionShortComponent exceptionComponent;

    private final ISecurityPathService securityPathService;

    private final ITokenCacheService tokenCacheService;

    private final MessageUtilComponent messageUtilComponent;

    private static final String AUTH_TOKEN_ERROR = "auth.token.error";
    private static final String AUTH_TOKEN_EXPIRED = "auth.token.expired";

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        return this.securityPathService.shouldNotFilter(request) ||
                this.securityPathService.isRefreshTokenPath(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) {

        if (this.securityPathService.isErrorPath(request.getRequestURI())) {
            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                log.debug("Error path processing: {}", e.getMessage());
            }
            return;
        }

        try {
            this.processAuthentication(request);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.debug("JWT token expired");
            String token = this.tokenOperations.extractJwtFromRequest(request);
            this.tokenOperations.handleTokenExpiration(token, e.getMessage());
            this.handleSecurityException(request, response,
                    this.exceptionComponent.tokenExpiredException(AUTH_TOKEN_EXPIRED, e.getMessage()));
        } catch (JwtTokenInvalidException | JwtTokenExpiredException e) {
            this.handleSecurityException(request, response, e);
        } catch (Exception e) {
            log.debug("Authentication error: {}", e.getMessage());
            this.handleSecurityException(request, response,
                    this.exceptionComponent.tokenInvalidException(AUTH_TOKEN_ERROR, e.getMessage()));
        }
    }

    private void handleSecurityException(
            @NonNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull BaseException ex) {
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            String resolvedMessage = this.messageUtilComponent.getMessage(ex.getMessageKey(), ex.getParameters());

            ApiErrorPojo errorResponse = ApiErrorPojo.of(
                    "Authentication Error",
                    resolvedMessage,
                    request.getRequestURI(),
                    HttpStatus.UNAUTHORIZED.name());

            response.getWriter().write(new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(errorResponse));
        } catch (IOException e) {
            log.error("Error writing error response", e);
        }
    }

    private void processAuthentication(HttpServletRequest request) {
        String jwt = this.tokenOperations.extractJwtFromRequest(request);

        if (!StringUtils.hasText(jwt)) {
            return;
        }

        this.validateAndProcessToken(jwt, request);
    }

    private void validateAndProcessToken(String jwt, HttpServletRequest request) {
        if (this.tokenOperations.validateToken(jwt)) {
            String username = this.tokenOperations.extractUsername(jwt);

            var userDetails = Optional.ofNullable(this.tokenCacheService.getUserDetailsFromCache(username))
                    .orElseGet(() -> {
                        UserSecurityDTO details = this.userService.findByUsername(username);
                        this.tokenCacheService.cacheUserDetails(username, details);
                        return details;
                    });

            var authentication = this.createAuthentication(username, userDetails);
            authentication.setDetails(new WebAuthenticationDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            this.tokenOperations.handleTokenAuthentication(jwt);
        }
    }

    @Contract("_, _ -> new")
    private @NotNull UsernamePasswordAuthenticationToken createAuthentication(String username,
                                                                              @NotNull UserSecurityDTO userDetails) {
        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }
}
