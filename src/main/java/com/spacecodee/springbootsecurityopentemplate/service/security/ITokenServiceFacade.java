package com.spacecodee.springbootsecurityopentemplate.service.security;

import com.spacecodee.springbootsecurityopentemplate.data.common.auth.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.record.TokenValidationResult;
import org.springframework.security.core.userdetails.UserDetails;

public interface ITokenServiceFacade {
    AuthenticationResponsePojo authenticateUser(UserDetails user);

    void logout(String token, String locale);

    void logoutByUserId(Integer userId, String locale);

    TokenValidationResult validateAndRefreshToken(String token, String locale);

    String refreshToken(String oldToken, UserDetails userDetails, String locale);

    boolean isValidToken(String token, String locale);

    String extractUsername(String token);
}
