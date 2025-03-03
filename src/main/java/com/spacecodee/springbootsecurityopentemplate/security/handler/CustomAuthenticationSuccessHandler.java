package com.spacecodee.springbootsecurityopentemplate.security.handler;

import com.spacecodee.springbootsecurityopentemplate.service.security.token.facade.TokenOperationsFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenOperationsFacade tokenOperationsFacade;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @NotNull Authentication authentication) {
        log.info("User {} successfully authenticated", authentication.getName());

        // If using form login, extract and activate the token
        try {
            String token = this.tokenOperationsFacade.extractJwtFromRequest(request);
            if (token != null && !token.isEmpty()) {
                this.tokenOperationsFacade.activateToken(token);
            }
        } catch (Exception e) {
            log.debug("No token to activate during form authentication: {}", e.getMessage());
        }
    }
}
