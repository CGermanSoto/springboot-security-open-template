package com.spacecodee.springbootsecurityopentemplate.security.util;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtProviderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final IJwtProviderService jwtProviderService;

    private final ExceptionShortComponent exceptionShortComponent;

    private final HttpServletRequest httpServletRequest;

    public Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw this.exceptionShortComponent.unauthorizedException("auth.request.null");
        }

        try {
            String token = this.jwtProviderService.extractJwtFromRequest(this.httpServletRequest);
            return this.jwtProviderService.extractUserId(token);
        } catch (Exception e) {
            log.error("Error getting current user ID", e);
            throw this.exceptionShortComponent.tokenInvalidException("auth.token.userid.invalid");
        }
    }

}