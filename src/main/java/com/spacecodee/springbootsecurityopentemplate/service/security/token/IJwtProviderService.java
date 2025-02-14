package com.spacecodee.springbootsecurityopentemplate.service.security.token;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.record.TokenClaims;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Map;

public interface IJwtProviderService {

    String generateToken(UserDetails user, Map<String, Object> extraClaims);

    Map<String, Object> generateExtraClaims(UserSecurityDTO userDetails);

    String buildToken(@NotNull TokenClaims tokenClaims);

    String extractUsername(String jwt);

    Integer extractUserId(String jwt);

    String extractJwtFromRequest(HttpServletRequest request);

    Instant extractExpiration(String jwt);

    Claims extractClaims(String jwt);

    Claims extractClaimsWithoutValidation(String jwt);

    boolean isTokenValid(String jwt);

}
