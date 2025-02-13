package com.spacecodee.springbootsecurityopentemplate.controller.api.auth.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.auth.IAuthController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.AuthResponseDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenInvalidException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtProviderService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.auth.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthControllerImpl extends AbstractController implements IAuthController {

    private final IAuthService authService;

    private final IJwtProviderService jwtProviderService;

    private final ExceptionShortComponent exceptionShortComponent;

    public AuthControllerImpl(MessageParameterHandler messageParameterHandler, IAuthService authService, IJwtProviderService jwtProviderService, ExceptionShortComponent exceptionShortComponent) {
        super(messageParameterHandler);
        this.authService = authService;
        this.jwtProviderService = jwtProviderService;
        this.exceptionShortComponent = exceptionShortComponent;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthResponseDTO>> login(@Valid LoginVO loginVO) {
        try {
            AuthResponseDTO authResponse = this.authService.login(loginVO);
            return ResponseEntity.ok(super.createDataResponse(
                    authResponse,
                    "auth.login.success",
                    HttpStatus.OK,
                    authResponse.getUsername()));
        } catch (BadCredentialsException e) {
            throw this.exceptionShortComponent.invalidCredentialsException(
                    "auth.invalid.credentials",
                    loginVO.getUsername());
        } catch (Exception e) {
            log.error("Unexpected error during login: ", e);
            throw this.exceptionShortComponent.tokenUnexpectedException("token.unexpected");
        }
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthResponseDTO>> refreshToken(HttpServletRequest request) {
        try {
            String token = this.jwtProviderService.extractJwtFromRequest(request);
            AuthResponseDTO refreshedAuth = this.authService.refreshToken(token);
            return ResponseEntity.ok(super.createDataResponse(
                    refreshedAuth,
                    "auth.token.refresh.success",
                    HttpStatus.OK,
                    refreshedAuth.getUsername()));
        } catch (JwtTokenExpiredException e) {
            throw this.exceptionShortComponent.tokenExpiredException("token.expired");
        } catch (JwtTokenInvalidException e) {
            throw this.exceptionShortComponent.tokenInvalidException("token.invalid");
        } catch (Exception e) {
            log.error("Error refreshing token: ", e);
            throw this.exceptionShortComponent.tokenUnexpectedException("token.refresh.failed");
        }
    }

    @Override
    public ResponseEntity<ApiResponsePojo> logout(HttpServletRequest request) {
        try {
            this.authService.logout(request);
            return ResponseEntity.ok(super.createResponse(
                    "auth.logout.success",
                    HttpStatus.OK,
                    this.jwtProviderService.extractUsername(this.jwtProviderService.extractJwtFromRequest(request))));
        } catch (Exception e) {
            log.error("Error during logout: ", e);
            throw this.exceptionShortComponent.tokenUnexpectedException("auth.logout.failed");
        }
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Boolean>> validateToken(HttpServletRequest request) {
        try {
            String token = this.jwtProviderService.extractJwtFromRequest(request);
            boolean isValid = this.authService.validateToken(token);
            return ResponseEntity.ok(super.createDataResponse(
                    isValid,
                    "auth.token.validate.success",
                    HttpStatus.OK,
                    token));
        } catch (JwtTokenExpiredException e) {
            throw this.exceptionShortComponent.tokenExpiredException("token.expired");
        } catch (JwtTokenInvalidException e) {
            throw this.exceptionShortComponent.tokenInvalidException("token.invalid");
        } catch (Exception e) {
            log.error("Error validating token: ", e);
            throw this.exceptionShortComponent.tokenUnexpectedException("token.unexpected");
        }
    }

}
