package com.spacecodee.springbootsecurityopentemplate.service.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginUserVO;
import com.spacecodee.springbootsecurityopentemplate.mappers.IJwtTokenMapper;
import com.spacecodee.springbootsecurityopentemplate.security.jwt.service.IJwtService;
import com.spacecodee.springbootsecurityopentemplate.service.IAuthenticationService;
import com.spacecodee.springbootsecurityopentemplate.service.IJwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IJwtTokenService jwtTokenService;
    private final IJwtTokenMapper jwtTokenMapper;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Override
    public AuthenticationResponsePojo login(String locale, LoginUserVO userVO) {
        AuthenticationResponsePojo newToken = this.validateExistsValidToken(userVO);
        if (newToken != null) return newToken;

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
        return null;
    }

    @Override
    public void logout(String locale, HttpServletRequest request) {
        var jwtToken = this.jwtService.extractJwtFromRequest(request);
        var existsToken = this.jwtTokenService.existsByJwtTokenToken(locale, jwtToken);
        if (!existsToken) {
            logger.warn("Token not found in the database");
        } else {
            this.jwtTokenService.deleteByToken(locale, jwtToken);
            logger.info("Token invalidated successfully");
        }
    }

    private @NotNull @UnmodifiableView Map<String, Object> generateExtraClaims(@NotNull UserDetailsDTO userDetailsDTO) {
        return Collections.unmodifiableMap(Map.of(
                "name", userDetailsDTO.getName(),
                "role", userDetailsDTO.getUserDetailsRoleDTO().getName(),
                "authorities", userDetailsDTO.getAuthorities()
        ));
    }

    private AuthenticationResponsePojo validateExistsValidToken(LoginUserVO userVO) {
        // Check if a JWT already exists for the user
        var existingToken = this.jwtTokenService.findTokenByUsername(userVO.getUsername());

        if (existingToken.isPresent()) {
            var newToken = existingToken.get();
            if (newToken.isValid()) {
                logger.info("Token is still valid, returning the token");
                return new AuthenticationResponsePojo(newToken.token());
            }
        }
        return null;
    }
}