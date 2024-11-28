package com.spacecodee.springbootsecurityopentemplate.security.authentication.filter;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.TokenNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenService;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.details.IUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final IUserDetailsService userService;
    private final IJwtTokenService jwtTokenService;
    private final Logger jwtAuthFilterLogger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    // Check if the JWT token is valid and set the authentication token
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        var jwt = this.jwtService.extractJwtFromRequest(request);
        if (!StringUtils.hasText(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get locale early
        var locale = LocaleResolverFilter.getCurrentLocale();

        try {
            var jwtTokenDTO = this.jwtTokenService.findBySecurityToken(locale, jwt);
            var isValid = this.validateToken(jwtTokenDTO);

            if (!isValid) {
                filterChain.doFilter(request, response);
                return;
            }

            var username = this.jwtService.extractUsername(jwt);
            var userDetailsDTO = this.userService.findByUsername(locale, username);

            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    userDetailsDTO.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (TokenNotFoundException e) {
            this.jwtAuthFilterLogger.log(Level.WARNING, "JWT validation failed: {0}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateToken(SecurityJwtTokenDTO jwtTokenDTO) {
        if (jwtTokenDTO == null) {
            return false;
        }

        var now = new Date(System.currentTimeMillis());
        var isValid = jwtTokenDTO.isValid() && jwtTokenDTO.getExpiryDate().after(now);

        if (!isValid) {
            this.updateTokenStatus(jwtTokenDTO);
        }

        return isValid;
    }

    private void updateTokenStatus(@NotNull SecurityJwtTokenDTO token) {
        token.setValid(false);
        this.jwtTokenService.save(token);
    }
}
