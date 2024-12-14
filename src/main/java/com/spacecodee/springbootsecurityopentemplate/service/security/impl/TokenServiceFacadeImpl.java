package com.spacecodee.springbootsecurityopentemplate.service.security.impl;

import com.spacecodee.springbootsecurityopentemplate.data.common.auth.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.record.TokenClaims;
import com.spacecodee.springbootsecurityopentemplate.data.record.TokenValidationResult;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.TokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IJwtTokenMapper;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtProviderService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenManagementService;
import com.spacecodee.springbootsecurityopentemplate.service.security.ITokenServiceFacade;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceFacadeImpl implements ITokenServiceFacade {
    private final IJwtProviderService jwtProviderService;
    private final IJwtTokenManagementService tokenManagementService;
    private final IJwtTokenMapper jwtTokenMapper;
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    public AuthenticationResponsePojo authenticateUser(UserDetails userDetails, String locale) {
        if (userDetails == null) {
            log.error("Attempt to authenticate null user");
            throw this.exceptionShortComponent.invalidParameterException("auth.user.null", locale);
        }

        var existingToken = this.tokenManagementService.findActiveTokenByUsername(userDetails.getUsername());
        if (StringUtils.hasText(existingToken)) {
            try {
                this.deleteExpiredToken(existingToken, locale);
                log.info("Token is still valid for user: {}", userDetails.getUsername());
                return new AuthenticationResponsePojo(existingToken);
            } catch (TokenExpiredException e) {
                log.info("Token expired for user: {}, generating new token", userDetails.getUsername());
            }
        }

        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) userDetails;
        String token = jwtProviderService.generateToken(userDetails, this.generateExtraClaims(userDetailsDTO));
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
            throw this.exceptionShortComponent.tokenExpiredException("token.invalidation.failed", locale);
        }
    }

    private void deleteExpiredToken(String token, String locale) {
        try {
            if (!jwtProviderService.isTokenValid(token)) {
                // Token is invalid/expired, so delete it
                log.info("Token is expired, deleting it");
                this.tokenManagementService.invalidateToken(locale, token);
                throw new TokenExpiredException("token.expired", locale);
            }
            // Token is valid, continue normal flow
            log.debug("Token is still valid");
        } catch (TokenExpiredException e) {
            throw e; // Propagate the exception
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException e) {
            log.info("Invalid token structure: {}", e.getMessage());
            this.tokenManagementService.invalidateToken(locale, token);
            throw new TokenExpiredException("token.inValid", locale);
        } catch (Exception e) {
            log.error("Ups!, Unexpected error validating token: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("token.inValid", locale);
        }
    }

    @Override
    public TokenValidationResult validateAndRefreshToken(String token, String locale) {
        var tokenExists = this.tokenManagementService.existsToken(locale, token);

        if (!tokenExists) {
            log.info("Token does not exist in database, we can't let you continue");
            throw this.exceptionShortComponent.tokenNotFoundException("auth.unauthorized", locale);
        }

        try {
            if (jwtProviderService.isTokenValid(token)) {
                return new TokenValidationResult(token, false);
            }

            throw new TokenExpiredException("token.expired", locale);
        } catch (TokenExpiredException | SignatureException | MalformedJwtException | UnsupportedJwtException e) {
            log.info("JWT validation failed, deleting token: {}", e.getMessage());
            var expiredClaims = this.jwtProviderService.extractClaimsWithoutValidation(token);
            return this.handleExpiredToken(token, expiredClaims, locale);
        } catch (Exception e) {
            log.error("Unexpected error validating token: {}", e.getMessage());
            throw this.exceptionShortComponent.tokenInvalidException("token.inValid", locale);
        }
    }

    @Override
    public String refreshToken(String oldToken, UserDetails userDetails, String locale) {
        Claims claims = jwtProviderService.extractClaims(oldToken);
        String newToken = jwtProviderService.generateToken(userDetails, claims);
        var expiry = jwtProviderService.extractExpiration(newToken);

        tokenManagementService.invalidateToken(locale, oldToken);
        tokenManagementService
                .saveToken(jwtTokenMapper.toUVO(newToken, expiry, (int) ((UserDetailsDTO) userDetails).getId()));

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

    @Contract("_, _, _ -> new")
    private @NotNull TokenValidationResult handleExpiredToken(String jwt, Claims claims, String locale) {
        try {
            // Delete expired token
            this.tokenManagementService.invalidateToken(locale, jwt);
            log.info("Token deleted successfully");
            // Generate a new token with existing claims using buildToken
            String newToken = jwtProviderService.buildToken(new TokenClaims(null, claims));
            var expiry = jwtProviderService.extractExpiration(newToken);

            var saveVO = jwtTokenMapper.toUVO(newToken, expiry, (int) claims.get("userId"));
            this.tokenManagementService.saveToken(saveVO);
            return new TokenValidationResult(newToken, true);
        } catch (Exception e) {
            log.error("Error refreshing token", e);
            throw this.exceptionShortComponent.tokenExpiredException("token.refresh.failed", locale);
        }
    }

    private @NotNull @UnmodifiableView Map<String, Object> generateExtraClaims(@NotNull UserDetailsDTO userDetailsDTO) {
        return Map.of(
                "userId", userDetailsDTO.getId(),
                "name", userDetailsDTO.getName(),
                "role", userDetailsDTO.getUserDetailsRoleDTO().getName(),
                "authorities", userDetailsDTO.getAuthorities());
    }
}
