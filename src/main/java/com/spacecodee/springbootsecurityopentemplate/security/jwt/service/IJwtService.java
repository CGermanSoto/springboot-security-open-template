package com.spacecodee.springbootsecurityopentemplate.security.jwt.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

public interface IJwtService {

    String generateToken(@NonNull UserDetails user, Map<String, Object> extraClaims);

    String extractUsername(String jwt);

    String extractJwtFromRequest(HttpServletRequest request);

    Date extractExpiration(String jwt);

    boolean isTokenExpired(String jwt);
}
