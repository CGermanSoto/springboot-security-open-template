package com.spacecodee.springbootsecurityopentemplate.service.security.impl;

import com.spacecodee.springbootsecurityopentemplate.data.common.auth.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.record.TokenValidationResult;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.TokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IJwtTokenMapper;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtProviderService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenManagementService;
import com.spacecodee.springbootsecurityopentemplate.service.security.ITokenServiceFacade;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceFacadeImpl implements ITokenServiceFacade {
    private final IJwtProviderService jwtProviderService;
    private final IJwtTokenManagementService tokenManagementService;
    private final IJwtTokenMapper jwtTokenMapper;

    @Override
    public AuthenticationResponsePojo authenticateUser(UserDetails user) {
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) user;
        String token = jwtProviderService.generateToken(user, generateExtraClaims(userDetailsDTO));
        var expiry = jwtProviderService.extractExpiration(token);

        tokenManagementService.saveToken(
                jwtTokenMapper.toUVO(token, expiry, (int) userDetailsDTO.getId()));

        return new AuthenticationResponsePojo(token);
    }

    @Override
    public void logout(String token, String locale) {
        this.tokenManagementService.invalidateToken(locale, token);
    }

    @Override
    public void logoutByUserId(Integer userId, String locale) {
        try {
            this.tokenManagementService.invalidateUserTokens(locale, userId);
        } catch (Exception e) {
            log.error("Error invalidating tokens for user {}: {}", userId, e.getMessage());
            throw new TokenExpiredException("token.invalidation.failed", locale);
        }
    }

    @Override
    public TokenValidationResult validateAndRefreshToken(String token, String locale) {
        try {
            if (jwtProviderService.isTokenValid(token)) {
                return new TokenValidationResult(token, false);
            }

            Claims claims = jwtProviderService.extractClaims(token);
            String newToken = jwtProviderService.generateToken(null, claims);
            var expiry = jwtProviderService.extractExpiration(newToken);
            int userId = ((Number) claims.get("userId")).intValue();

            // Clean old token and save a new one
            tokenManagementService.invalidateToken(locale, token);
            tokenManagementService.saveToken(
                    jwtTokenMapper.toUVO(newToken, expiry, userId));

            return new TokenValidationResult(newToken, true);
        } catch (Exception e) {
            log.error("Error validating/refreshing token: {}", e.getMessage());
            this.tokenManagementService.invalidateToken(locale, token);
            throw new TokenExpiredException("token.expired", locale);
        }
    }

    @Override
    public String refreshToken(String oldToken, UserDetails userDetails, String locale) {
        Claims claims = jwtProviderService.extractClaims(oldToken);
        String newToken = jwtProviderService.generateToken(userDetails, claims);
        var expiry = jwtProviderService.extractExpiration(newToken);

        tokenManagementService.invalidateToken(locale, oldToken);
        tokenManagementService.saveToken(jwtTokenMapper.toUVO(newToken, expiry, (int) ((UserDetailsDTO) userDetails).getId()));

        return newToken;
    }

    @Override
    public boolean isValidToken(String token, String locale) {
        return tokenManagementService.existsToken(locale, token) &&
                jwtProviderService.isTokenValid(token);
    }

    @Override
    public String extractUsername(String token) {
        return this.jwtProviderService.extractUsername(token);
    }

    private @NotNull @UnmodifiableView Map<String, Object> generateExtraClaims(@NotNull UserDetailsDTO userDetailsDTO) {
        return Map.of(
                "userId", userDetailsDTO.getId(),
                "name", userDetailsDTO.getName(),
                "role", userDetailsDTO.getUserDetailsRoleDTO().getName(),
                "authorities", userDetailsDTO.getAuthorities());
    }
}
