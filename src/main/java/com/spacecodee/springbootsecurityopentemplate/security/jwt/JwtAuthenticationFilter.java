package com.spacecodee.springbootsecurityopentemplate.security.jwt;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.security.jwt.service.IJwtService;
import com.spacecodee.springbootsecurityopentemplate.service.IJwtTokenService;
import com.spacecodee.springbootsecurityopentemplate.service.user.details.IUserDetailsService;
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

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final IUserDetailsService userService;
    private final IJwtTokenService jwtTokenService;

    // Check if the JWT token is valid and set the authentication token
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        var jwt = this.jwtService.extractJwtFromRequest(request);
        if (!StringUtils.hasText(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwtTokenDTO = this.jwtTokenService.findBySecurityToken(jwt);
        var isValid = this.validateToken(jwtTokenDTO);

        if (!isValid) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the username from the JWT token
        var username = this.jwtService.extractUsername(jwt);

        // Retrieve user details using the extracted username
        var userDetailsDTO = this.userService.findByUsername(username);

        // Create an authentication token using the username and user authorities
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, null,
                userDetailsDTO.getAuthorities());

        // Set additional details for the authentication token
        authenticationToken.setDetails(new WebAuthenticationDetails(request));

        // Set the authentication token in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Continue the filter chain
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
