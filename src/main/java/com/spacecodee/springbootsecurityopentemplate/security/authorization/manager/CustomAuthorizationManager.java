package com.spacecodee.springbootsecurityopentemplate.security.authorization.manager;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsOperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsPermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.security.authentication.filter.LocaleResolverFilter;
import com.spacecodee.springbootsecurityopentemplate.service.core.endpoint.IOperationService;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.details.IUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@AllArgsConstructor
@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final IOperationService operationService;
    private final IUserDetailsService userService;

    private final Logger logger = Logger.getLogger(CustomAuthorizationManager.class.getName());

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
            @NotNull RequestAuthorizationContext object) {

        HttpServletRequest request = object.getRequest();
        var url = this.extractUrl(request);
        var httpMethod = request.getMethod();

        // First check if endpoint is public
        if (isPublic(url, httpMethod)) {
            return new AuthorizationDecision(true);
        }

        // If not public, verify user has required permissions
        var auth = authentication.get();
        if (auth == null || !auth.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        return new AuthorizationDecision(isGranted(auth, url, httpMethod));
    }

    private boolean isGranted(Authentication authentication, String url, String httpMethod) {
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            logger.warning("Invalid authentication type");
            return false;
        }

        var operations = obtainOperations(authentication);
        return operations.stream()
                .anyMatch(operation -> matches(operation, url, httpMethod));
    }

    private List<UserDetailsOperationDTO> obtainOperations(Authentication authentication) {
        var authToken = (UsernamePasswordAuthenticationToken) authentication;
        var username = authToken.getPrincipal().toString();
        var locale = LocaleResolverFilter.getCurrentLocale();
        var user = this.userService.findByUsername(locale, username);

        return user.getUserDetailsRoleDTO().getUserDetailsPermissionDTOList().stream()
                .map(UserDetailsPermissionDTO::getOperationDTO).toList();
    }

    private boolean isPublic(String url, String httpMethod) {
        var publicOperations = operationService.findByPublicAccess();
        return publicOperations.stream()
                .anyMatch(operation -> matches(operation, url, httpMethod));
    }

    private boolean matches(@NotNull UserDetailsOperationDTO operation, String url, String httpMethod) {
        var pattern = Pattern.compile(operation.getModuleDTO().getBasePath() + operation.getPath());
        return pattern.matcher(url).matches() &&
                operation.getHttpMethod().equalsIgnoreCase(httpMethod);
    }

    private @NotNull String extractUrl(@NotNull HttpServletRequest request) {
        var contextPath = request.getContextPath();
        var url = request.getRequestURI();
        url = url.replaceFirst(contextPath, "");

        String contextPathNew = "/api/v1";

        // Remove API context path if present
        if (url.startsWith(contextPathNew)) {
            url = url.substring(contextPathNew.length());
        }

        // Return immediately for Swagger UI paths
        if (isSwaggerUIPath(url)) {
            return url;
        }

        return url;
    }

    private boolean isSwaggerUIPath(String url) {
        return url.startsWith("/v3/api-docs") ||
                url.startsWith("/swagger-ui/") ||
                url.equals("/swagger-ui.html") ||
                url.startsWith("/webjars/");
    }
}