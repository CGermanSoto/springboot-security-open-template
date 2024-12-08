package com.spacecodee.springbootsecurityopentemplate.service.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

public interface IJwtProviderService {

    String generateToken(UserDetails user, Map<String, Object> extraClaims);

    String extractUsername(String jwt);

    String extractJwtFromRequest(HttpServletRequest request);

    Date extractExpiration(String jwt);

    Claims extractClaims(String jwt);

    Claims extractClaimsWithoutValidation(String jwt);

    boolean isTokenValid(String jwt);
}
