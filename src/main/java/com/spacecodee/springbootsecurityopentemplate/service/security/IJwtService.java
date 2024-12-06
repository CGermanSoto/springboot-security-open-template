package com.spacecodee.springbootsecurityopentemplate.service.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;

import org.springframework.security.core.userdetails.UserDetails;

import com.spacecodee.springbootsecurityopentemplate.data.record.TokenValidationResult;

import java.util.Date;
import java.util.Map;

public interface IJwtService {

    String generateToken(@NonNull UserDetails user, Map<String, Object> extraClaims);

    String extractUsername(String jwt);

    String extractJwtFromRequest(HttpServletRequest request);

    Date extractExpiration(String jwt);

    boolean isTokenExpired(String jwt);

    String refreshToken(String oldToken, UserDetails userDetails);

    TokenValidationResult validateToken(String jwt, String locale);
}
