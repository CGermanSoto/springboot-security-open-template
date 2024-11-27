package com.spacecodee.springbootsecurityopentemplate.service.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginUserVO;
import com.spacecodee.springbootsecurityopentemplate.mappers.IJwtTokenMapper;
import com.spacecodee.springbootsecurityopentemplate.security.jwt.service.IJwtService;
import com.spacecodee.springbootsecurityopentemplate.service.IAuthenticationService;
import com.spacecodee.springbootsecurityopentemplate.service.IJwtTokenService;
import com.spacecodee.springbootsecurityopentemplate.service.user.details.IUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IUserDetailsService userDetailsService;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IJwtTokenService jwtTokenService;
    private final IJwtTokenMapper jwtTokenMapper;

    private final Logger logger = Logger.getLogger(AuthenticationServiceImpl.class.getName());

    @Override
    public AuthenticationResponsePojo login(String locale, LoginUserVO userVO) {
        this.validateValidToken(locale, userVO);

        var authentication = new UsernamePasswordAuthenticationToken(userVO.getUsername(), userVO.getPassword());
        Authentication authResult = this.authenticationManager.authenticate(authentication);
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) authResult.getPrincipal();

        String jwt = this.jwtService.generateToken(userDetailsDTO, this.generateExtraClaims(userDetailsDTO));

        var expiry = this.jwtService.extractExpiration(jwt);
        this.jwtTokenService.save(this.jwtTokenMapper.toUVO(jwt, expiry, (int) userDetailsDTO.getId()));

        return new AuthenticationResponsePojo(jwt);
    }

    @Override
    public boolean validateToken(String locale, String jwt) {
        try {
            this.jwtService.extractUsername(jwt);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDetailsDTO findLoggedInUser(String locale) {
        var auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        var username = auth.getPrincipal().toString();
        return this.userDetailsService.findByUsername(username);
    }

    @Override
    public void logout(String locale, HttpServletRequest request) {
        var jwtToken = this.jwtService.extractJwtFromRequest(request);
        var existsToken = this.jwtTokenService.existsByJwtTokenToken(locale, jwtToken);
        if (!existsToken) {
            this.logger.warning("Token not found in the database");
        } else {
            this.jwtTokenService.deleteByToken(locale, jwtToken);
            logger.info("Token invalidated successfully");
        }
    }

    private @NotNull @UnmodifiableView Map<String, Object> generateExtraClaims(@NotNull UserDetailsDTO userDetailsDTO) {
        return Map.of(
                "name", userDetailsDTO.getName(),
                "role", userDetailsDTO.getUserDetailsRoleDTO().getName(),
                "authorities", userDetailsDTO.getAuthorities()
        );
    }

    private void validateValidToken(String locale, @NotNull LoginUserVO userVO) {
        var existingToken = this.jwtTokenService.getTokenByUsername(userVO.getUsername());
        if (StringUtils.hasText(existingToken)) {
            var isTokenValid = this.jwtService.isTokenExpired(existingToken);

            if (isTokenValid) {
                this.logger.info("Token is still valid, returning the token");
            } else {
                this.logger.info("Token is expired, invalidating the token");
                this.jwtTokenService.deleteByToken(locale, existingToken);
            }
        }
    }

    private @Nullable AuthenticationResponsePojo validateExistsValidToken(String locale, @NotNull LoginUserVO userVO) {
        // Check if a JWT already exists for the user
        var existingToken = this.jwtTokenService.findTokenByUsername(userVO.getUsername());
        this.logger.log(Level.INFO, "Existing token: {0}", existingToken);
        if (existingToken.isPresent()) {
            var newToken = existingToken.get();
            var isTokenValid = this.jwtService.isTokenExpired(newToken.token());

            if (isTokenValid) {
                this.logger.info("Token is still valid, returning the token");
                return new AuthenticationResponsePojo(newToken.token());
            } else {
                this.logger.info("Token is expired, invalidating the token");
                this.jwtTokenService.deleteByToken(locale, newToken.token());
            }
        }
        return null;
    }
}