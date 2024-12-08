package com.spacecodee.springbootsecurityopentemplate.service.auth.impl;

import com.spacecodee.springbootsecurityopentemplate.data.common.auth.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginUserVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.TokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.auth.IAuthenticationService;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.details.IUserDetailsService;
import com.spacecodee.springbootsecurityopentemplate.service.security.ITokenServiceFacade;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final ITokenServiceFacade tokenServiceFacade;
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    public AuthenticationResponsePojo login(String locale, @NotNull LoginUserVO userVO) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userVO.username(), userVO.password());
        Authentication authResult;
        // Authenticate user
        try {
            authResult = this.authenticationManager.authenticate(authentication);
        } catch (InternalAuthenticationServiceException e) {
            log.error("Error authenticating user: {}", e.getMessage());
            throw this.exceptionShortComponent.invalidCredentialsException("auth.invalid.credentials", locale);
        } catch (Exception e) {
            log.error("Server error: {}", e.getMessage());
            throw this.exceptionShortComponent.invalidParameterException("error.server", locale);
        }

        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) authResult.getPrincipal();

        // Use facade to handle token operations
        return this.tokenServiceFacade.authenticateUser(userDetailsDTO, locale);
    }

    @Override
    public boolean validateToken(String locale, String jwt) {
        return this.tokenServiceFacade.isValidToken(jwt, locale);
    }

    @Override
    public UserDetailsDTO findLoggedInUser(String locale) {
        var auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var username = auth.getPrincipal().toString();
        return this.userDetailsService.findByUsername(locale, username);
    }

    @Override
    public void logout(String locale, HttpServletRequest request) {
        var jwt = extractTokenFromRequest(request);
        if (jwt != null) {
            this.tokenServiceFacade.logout(jwt, locale);
        }
    }

    @Override
    public AuthenticationResponsePojo refreshToken(String locale, HttpServletRequest request) {
        var token = extractTokenFromRequest(request);
        if (token == null) {
            throw this.exceptionShortComponent.tokenNotFoundException("token.not.found", locale);
        }

        var username = extractTokenUsername(token);
        var userDetails = this.userDetailsService.findByUsername(locale, username);

        var newToken = this.tokenServiceFacade.refreshToken(token, userDetails, locale);
        return new AuthenticationResponsePojo(newToken);
    }

    private @Nullable String extractTokenFromRequest(@NotNull HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String extractTokenUsername(String token) {
        try {
            return this.tokenServiceFacade.extractUsername(token);
        } catch (Exception e) {
            log.error("Error extracting username from token: {}", e.getMessage());
            throw new TokenExpiredException("token.expired", "en");
        }
    }
}