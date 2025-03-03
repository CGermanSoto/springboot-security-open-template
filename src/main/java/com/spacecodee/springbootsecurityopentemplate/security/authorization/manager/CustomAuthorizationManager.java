package com.spacecodee.springbootsecurityopentemplate.security.authorization.manager;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.PermissionSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.security.path.ISecurityPathService;
import com.spacecodee.springbootsecurityopentemplate.cache.IPermissionCacheService;
import com.spacecodee.springbootsecurityopentemplate.cache.ISecurityCacheService;
import com.spacecodee.springbootsecurityopentemplate.cache.ITokenCacheService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.facade.TokenOperationsFacade;
import com.spacecodee.springbootsecurityopentemplate.service.security.user.IUserSecurityService;
import com.spacecodee.springbootsecurityopentemplate.utils.PathUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final String INVALID_AUTH_TYPE = "Invalid authentication type";

    @Value("${app.api.context-path}")
    private String contextPath;

    private final ISecurityCacheService securityCacheService;

    private final IUserSecurityService userSecurityService;

    private final ISecurityPathService securityPathService;

    private final ITokenCacheService tokenCacheService;

    private final TokenOperationsFacade tokenOperationsFacade;

    private final IPermissionCacheService permissionCacheService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       @NotNull RequestAuthorizationContext context) {
        return Optional.of(context)
                .map(this::extractRequest)
                .filter(request -> !this.isPublicAccess(request))
                .map(request -> this.validateAuthentication(authentication.get(), request))
                .orElseGet(() -> new AuthorizationDecision(true));
    }

    @Contract(pure = true)
    private HttpServletRequest extractRequest(@NotNull RequestAuthorizationContext context) {
        return context.getRequest();
    }

    private boolean isPublicAccess(HttpServletRequest request) {
        return this.securityPathService.shouldNotFilter(request) ||
                this.securityPathService.isRefreshTokenPath(request.getRequestURI());
    }

    @Contract("_, _ -> new")
    private @NotNull AuthorizationDecision validateAuthentication(Authentication authentication,
                                                                  HttpServletRequest request) {
        if (!this.isValidAuthentication(authentication)) {
            log.warn(CustomAuthorizationManager.INVALID_AUTH_TYPE);
            return new AuthorizationDecision(false);
        }

        // Check token state
        try {
            String token = this.tokenOperationsFacade.extractJwtFromRequest(request);
            TokenStateEnum currentState = this.tokenOperationsFacade.getTokenState(token);

            if (currentState != TokenStateEnum.ACTIVE) {
                log.warn("Token is not active. Current state: {}", currentState);
                this.tokenOperationsFacade.handleTokenExpiration(token, "Token state check failed");
                this.tokenCacheService.cacheTokenState(token, currentState);
                return new AuthorizationDecision(false);
            }

            String url = this.extractUrl(request);
            String httpMethod = request.getMethod();

            return new AuthorizationDecision(this.hasRequiredPermissions(authentication, url, httpMethod));
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            return new AuthorizationDecision(false);
        }
    }

    private boolean isValidAuthentication(Authentication authentication) {
        return authentication != null &&
                authentication.isAuthenticated() &&
                authentication instanceof UsernamePasswordAuthenticationToken;
    }

    private boolean hasRequiredPermissions(Authentication authentication, String url, String httpMethod) {
        try {
            String username = authentication.getPrincipal().toString();
            var userDetails = this.userSecurityService.findByUsername(username);
            String roleId = String.valueOf(userDetails.getRoleSecurityDTO().getId());

            // Get permissions from cache
            List<String> permissions = this.permissionCacheService.getPermissionsFromCache(roleId);

            // Build the request permission pattern
            String requestedPermission = httpMethod + ":" + url;

            if (permissions == null || permissions.isEmpty()) {
                var operations = this.securityCacheService.getUserOperations(username,
                        () -> userDetails.getRoleSecurityDTO().getPermissionDTOList().stream()
                                .map(PermissionSecurityDTO::getOperationDTO)
                                .toList());

                permissions = operations.stream()
                        .map(op -> op.getHttpMethod() + ":" +
                                (op.getModuleSecurityDTO() != null ? op.getModuleSecurityDTO().getBasePath() : "") +
                                op.getPath())
                        .toList();

                this.permissionCacheService.cachePermissions(roleId, permissions);
            }

            return permissions.stream()
                    .anyMatch(permission -> PathUtils.matchesPattern(permission, requestedPermission));
        } catch (Exception e) {
            log.error("Error checking permissions: {}", e.getMessage(), e);
            return false;
        }
    }

    private boolean matchesOperation(@NotNull OperationSecurityDTO operation, String url, String httpMethod) {
        String operationPath = operation.getModuleSecurityDTO().getBasePath() + operation.getPath();
        boolean matches = PathUtils.matchesPattern(operationPath, url);

        return matches && operation.getHttpMethod().equalsIgnoreCase(httpMethod);
    }

    private @NotNull String extractUrl(@NotNull HttpServletRequest request) {
        String url = request.getRequestURI();

        // Remove the context path and api/v1 prefix
        if (url.startsWith(this.contextPath)) {
            url = url.substring(this.contextPath.length());
        }

        return url;
    }

}