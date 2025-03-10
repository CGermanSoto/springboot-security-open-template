package com.spacecodee.springbootsecurityopentemplate.service.security.auth.impl;

import com.spacecodee.springbootsecurityopentemplate.cache.ITokenCacheService;
import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.AuthResponseDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.UpdateJwtTokenVO;
import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.security.auth.IAuthMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtProviderService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtTokenSecurityService;
import com.spacecodee.springbootsecurityopentemplate.service.security.auth.IAuthService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.lifecycle.ITokenLifecycleService;
import com.spacecodee.springbootsecurityopentemplate.service.security.user.IUserSecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IJwtProviderService jwtProviderService;

    private final IJwtTokenSecurityService jwtTokenSecurityService;

    private final IUserSecurityService userSecurityService;

    private final IAuthMapper authMapper;

    private final ITokenLifecycleService tokenLifecycleService;

    private final AuthenticationManager authenticationManager;

    private final ExceptionShortComponent exceptionComponent;

    private final ITokenCacheService tokenCacheService;

    @Override
    @Transactional(noRollbackFor = JwtTokenNotFoundException.class)
    public AuthResponseDTO login(@NotNull LoginVO loginVO) {
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginVO.getUsername(), loginVO.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserEntity user = this.userSecurityService.findUserEntityByUsername(userDetails.getUsername());
            UserSecurityDTO userSecurityDTO = (UserSecurityDTO) userDetails;

            this.tokenCacheService.cacheUserDetails(user.getUsername(), userSecurityDTO);

            JwtTokenEntity existingToken = this.jwtTokenSecurityService.handleExistingToken(user.getUsername(), true);

            if (existingToken != null) {
                if (existingToken.isValid() && jwtProviderService.isTokenValid(existingToken.getToken())) {
                    tokenLifecycleService.activateToken(existingToken.getToken());

                    this.tokenCacheService.cacheToken(existingToken.getToken(), existingToken);
                    this.tokenCacheService.cacheTokenState(existingToken.getToken(), TokenStateEnum.ACTIVE);

                    return this.authMapper.toAuthResponseDTO(existingToken, user);
                }

                JwtTokenEntity refreshedToken = this.jwtTokenSecurityService
                        .refreshExistingTokenOnLogin(userSecurityDTO, existingToken);

                this.handleTokenLifecycleAndCaching(refreshedToken.getToken(), refreshedToken, user.getUsername(),
                        "refresh");

                return this.authMapper.toAuthResponseDTO(refreshedToken, user);
            }

            JwtTokenEntity newToken = this.jwtTokenSecurityService.createNewTokenInLogin(userSecurityDTO, user);

            this.handleTokenLifecycleAndCaching(newToken.getToken(), newToken, user.getUsername(), "new");

            return this.authMapper.toAuthResponseDTO(newToken, user);

        } catch (BadCredentialsException e) {
            throw this.exceptionComponent.invalidCredentialsException("auth.invalid.credentials",
                    loginVO.getUsername());
        } catch (Exception e) {
            log.error("Unexpected error during login: ", e);
            throw this.exceptionComponent.tokenUnexpectedException("token.unexpected");
        }
    }

    /**
     * Handles token lifecycle operations and caching
     *
     * @param token         The token string
     * @param tokenEntity   The token entity object
     * @param username      The username associated with the token
     * @param operationType The type of operation (new/refresh)
     */
    private void handleTokenLifecycleAndCaching(String token, JwtTokenEntity tokenEntity,
            String username, String operationType) {
        try {
            if ("new".equals(operationType)) {
                this.tokenLifecycleService.initiateToken(token, username);
                this.tokenLifecycleService.activateToken(token);
            } else if ("refresh".equals(operationType)) {
                this.tokenLifecycleService.refreshToken(token, tokenEntity.getToken());
            }

            tokenCacheService.cacheToken(token, tokenEntity);
            tokenCacheService.cacheTokenState(token, TokenStateEnum.ACTIVE);
        } catch (Exception e) {
            log.warn("Non-critical error during {} token lifecycle operations: {}", operationType, e.getMessage());
        }
    }

    @Override
    @Transactional
    public AuthResponseDTO refreshToken(@NotNull String token) {
        try {
            Claims claims = this.jwtProviderService.extractClaimsWithoutValidation(token);
            String username = claims.getSubject();
            UserEntity user = this.userSecurityService.findUserEntityByUsername(username);

            JwtTokenEntity currentToken = this.jwtTokenSecurityService.findByToken(token);

            UserSecurityDTO userSecurityDTO = this.authMapper.toAuthUserSecurityDTO(user);
            Map<String, Object> extraClaims = this.jwtProviderService.generateExtraClaims(userSecurityDTO);
            String newToken = this.jwtProviderService.generateToken(userSecurityDTO, extraClaims);
            Instant expiryDate = this.jwtProviderService.extractExpiration(newToken);

            UpdateJwtTokenVO updateTokenVO = this.authMapper.toUpdateJwtTokenVO(currentToken, newToken, expiryDate);
            this.jwtTokenSecurityService.updateTokenToRefresh(updateTokenVO);

            JwtTokenEntity updatedToken = this.jwtTokenSecurityService.findByToken(newToken);

            // Replace nested try-catch with method call
            this.updateTokenCache(token, newToken, updatedToken, username, userSecurityDTO);

            return this.authMapper.toAuthResponseDTO(updatedToken, user);
        } catch (ExpiredJwtException e) {
            throw this.exceptionComponent.tokenExpiredException("token.expired");
        } catch (SecurityException | MalformedJwtException e) {
            throw this.exceptionComponent.tokenInvalidException("token.invalid");
        } catch (Exception e) {
            log.error("Error refreshing token: ", e);
            throw this.exceptionComponent.tokenUnexpectedException("token.refresh.failed");
        }
    }

    /**
     * Updates token cache during token refresh operations
     *
     * @param oldToken       The old token to be removed from cache
     * @param newToken       The new token to be added to cache
     * @param newTokenEntity The entity for the new token
     * @param username       The username associated with the token
     * @param userDetails    The user security details
     */
    private void updateTokenCache(String oldToken, String newToken, JwtTokenEntity newTokenEntity,
            String username, UserSecurityDTO userDetails) {
        try {
            this.tokenCacheService.removeFromCache(oldToken);
            this.tokenCacheService.cacheToken(newToken, newTokenEntity);
            this.tokenCacheService.cacheTokenState(newToken, TokenStateEnum.ACTIVE);
            this.tokenCacheService.cacheUserDetails(username, userDetails);

            log.debug("Token cache updated successfully during token refresh");
        } catch (Exception e) {
            log.warn("Non-critical error updating token cache during refresh: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request) {
        try {
            String token = this.jwtProviderService.extractJwtFromRequest(request);
            String username = this.jwtProviderService.extractUsername(token);

            this.tokenLifecycleService.revokeToken(token);
            this.tokenLifecycleService.markTokenAsExpired(token, "User logout");
            this.tokenLifecycleService.handleTokenAccess(token, "Logout operation");
            this.updateCacheOnLogout(token, username);

            log.info("User {} logged out successfully", username);
        } catch (Exception e) {
            log.error("Error during logout: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("auth.logout.failed");
        }
    }

    /**
     * Updates the token cache during logout operations
     *
     * @param token    The token being invalidated
     * @param username The username associated with the token
     */
    private void updateCacheOnLogout(String token, String username) {
        try {
            this.tokenCacheService.removeFromCache(token);

            log.debug("Token cache updated successfully during logout for user: {}", username);
        } catch (Exception e) {
            log.warn("Non-critical error updating token cache during logout: {}", e.getMessage());
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            JwtTokenEntity tokenEntity = this.jwtTokenSecurityService.findByToken(token);
            if (!tokenEntity.isValid()) {
                log.debug("Token is marked as invalid in database");
                return false;
            }

            boolean isValid = this.jwtProviderService.isTokenValid(token);
            if (!isValid) {
                log.debug("Token failed JWT validation");
                return false;
            }

            TokenStateEnum currentState = this.tokenLifecycleService.getTokenState(token);
            if (currentState != TokenStateEnum.ACTIVE) {
                log.debug("Token is not in ACTIVE state. Current state: {}", currentState);
                return false;
            }

            this.tokenLifecycleService.handleTokenAccess(token, "Token validation");

            return true;
        } catch (JwtTokenNotFoundException e) {
            log.debug("Token not found in database");
            return false;
        } catch (Exception e) {
            log.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }
}
