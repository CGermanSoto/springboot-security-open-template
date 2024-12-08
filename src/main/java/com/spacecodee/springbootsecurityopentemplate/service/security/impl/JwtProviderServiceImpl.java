package com.spacecodee.springbootsecurityopentemplate.service.security.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtProviderService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtProviderServiceImpl implements IJwtProviderService {

    private final ExceptionShortComponent exceptionShortComponent;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-in-minutes}")
    private long expirationInMinutes;

    public JwtProviderServiceImpl(ExceptionShortComponent exceptionShortComponent) {
        this.exceptionShortComponent = exceptionShortComponent;
    }

    @Override
    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
        if (user == null) {
            log.error("Attempt to generate token for null user");
            throw this.exceptionShortComponent.invalidParameterException("auth.user.null", "en");
        }

        var claims = extraClaims != null ? extraClaims : new HashMap<String, Object>();
        var issuedAt = new Date(System.currentTimeMillis());
        var expiration = new Date(System.currentTimeMillis() + (this.expirationInMinutes * 60 * 1000));

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(claims)
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String extractUsername(String jwt) {
        return extractClaims(jwt).getSubject();
    }

    @Override
    public String extractJwtFromRequest(HttpServletRequest request) {
        if (request == null) {
            log.error("Attempt to extract token from null request");
            throw this.exceptionShortComponent.invalidParameterException("auth.request.null", "en");
        }

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public Date extractExpiration(String jwt) {
        return extractClaims(jwt).getExpiration();
    }

    @Override
    public Claims extractClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    @Override
    public boolean isTokenValid(String jwt) {
        try {
            Claims claims = extractClaims(jwt);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }

    private @NotNull SecretKey generateKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}