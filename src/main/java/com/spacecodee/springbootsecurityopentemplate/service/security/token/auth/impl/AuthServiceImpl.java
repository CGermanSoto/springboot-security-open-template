package com.spacecodee.springbootsecurityopentemplate.service.security.token.auth.impl;

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
import com.spacecodee.springbootsecurityopentemplate.service.security.token.auth.IAuthService;
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

    @Override
    @Transactional(noRollbackFor = JwtTokenNotFoundException.class)
    public AuthResponseDTO login(@NotNull LoginVO loginVO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginVO.getUsername(), loginVO.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserEntity user = userSecurityService.findUserEntityByUsername(userDetails.getUsername());
            UserSecurityDTO userSecurityDTO = (UserSecurityDTO) userDetails;

            JwtTokenEntity existingToken = this.jwtTokenSecurityService.handleExistingToken(user.getUsername(), true);

            if (existingToken != null) {
                if (existingToken.isValid() && jwtProviderService.isTokenValid(existingToken.getToken())) {
                    return this.authMapper.toAuthResponseDTO(existingToken, user);
                }

                JwtTokenEntity refreshedToken = jwtTokenSecurityService.refreshExistingTokenOnLogin(
                        userSecurityDTO,
                        existingToken);
                return authMapper.toAuthResponseDTO(refreshedToken, user);
            }

            JwtTokenEntity newToken = jwtTokenSecurityService.createNewTokenInLogin(userSecurityDTO, user);
            return authMapper.toAuthResponseDTO(newToken, user);

        } catch (BadCredentialsException e) {
            throw exceptionComponent.invalidCredentialsException("auth.invalid.credentials",
                    loginVO.getUsername());
        } catch (Exception e) {
            log.error("Unexpected error during login: ", e);
            throw exceptionComponent.tokenUnexpectedException("token.unexpected");
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

    @Override
    @Transactional
    public void logout(HttpServletRequest request) {
        try {
            String token = this.jwtProviderService.extractJwtFromRequest(request);
            String username = this.jwtProviderService.extractUsername(token);

            this.tokenLifecycleService.revokeToken(token);
            this.tokenLifecycleService.markTokenAsExpired(token, "User logout");
            this.tokenLifecycleService.handleTokenAccess(token, "Logout operation");

            log.info("User {} logged out successfully", username);
        } catch (Exception e) {
            log.error("Error during logout: {}", e.getMessage());
            throw this.exceptionComponent.tokenUnexpectedException("auth.logout.failed");
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
