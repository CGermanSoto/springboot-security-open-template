package com.spacecodee.springbootsecurityopentemplate.security.authentication.filter;

import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenUnexpectedException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.details.IUserDetailsService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtProviderService;
import com.spacecodee.springbootsecurityopentemplate.service.security.ITokenServiceFacade;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ITokenServiceFacade tokenServiceFacade;
    private final IJwtProviderService jwtProviderService; // Still needed for extractJwtFromRequest
    private final IUserDetailsService userService;
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        var jwt = this.jwtProviderService.extractJwtFromRequest(request);
        if (!StringUtils.hasText(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        var locale = LocaleResolverFilter.getCurrentLocale();

        try {
            var validationResult = this.tokenServiceFacade.validateAndRefreshToken(jwt, locale);

            if (validationResult.wasRefreshed()) {
                response.setHeader("Authorization", "Bearer " + validationResult.token());
            }

            var username = this.tokenServiceFacade.extractUsername(validationResult.token());
            var userDetailsDTO = this.userService.findByUsername(locale, username);

            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    userDetailsDTO.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JwtTokenUnexpectedException e) {
            log.warn("There was an unexpected error when we were trying to refresh the token, please log in again: {}",
                    e.getMessage());
            throw this.exceptionShortComponent.tokenUnexpectedException("token.unexpected", locale);
        }

        filterChain.doFilter(request, response);
    }
}
