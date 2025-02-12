package com.spacecodee.springbootsecurityopentemplate.controller.api.auth.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.auth.IAuthenticationController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.auth.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.auth.IAuthenticationService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtProviderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationControllerImpl extends AbstractController implements IAuthenticationController {
    private final IJwtProviderService jwtService;
    private final IAuthenticationService authenticationService;

    public AuthenticationControllerImpl(MessageUtilComponent messageUtilComponent,
                                        MessageParameterHandler messageParameterHandler,
                                        IJwtProviderService jwtService,
                                        IAuthenticationService authenticationService) {
        super(messageUtilComponent, messageParameterHandler);
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Boolean>> validate(String locale, HttpServletRequest request) {
        var jwt = this.jwtService.extractJwtFromRequest(request);
        String username = this.jwtService.extractUsername(jwt);

        if (jwt == null) {
            return ResponseEntity.badRequest()
                    .body(super.createDataResponse(false, "token.missing", locale,
                            HttpStatus.BAD_REQUEST, username));
        }

        boolean isTokenValid = this.authenticationService.validateToken(locale, jwt);
        return ResponseEntity.ok(super.createDataResponse(isTokenValid,
                isTokenValid ? "token.valid" : "token.inValid", locale,
                HttpStatus.OK, username));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> authenticate(String locale,
                                                                                        LoginVO request) {
        var response = this.authenticationService.login(locale, request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(super.createDataResponse(response, "login.success", locale,
                        HttpStatus.ACCEPTED, request.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<UserDetailsDTO>> profile(String locale) {
        var user = this.authenticationService.findLoggedInUser(locale);
        return ResponseEntity.ok(super.createDataResponse(user,
                "user.profile", locale, HttpStatus.OK, user.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> logout(String locale, HttpServletRequest request) {
        var username = this.jwtService.extractUsernameFromRequest(request);
        this.authenticationService.logout(locale, request);
        return ResponseEntity.ok(super.createResponse("logout.success", locale,
                HttpStatus.OK, username));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> refreshToken(
            String locale, HttpServletRequest request) {
        var username = this.jwtService.extractUsernameFromRequest(request);
        var response = this.authenticationService.refreshToken(locale, request);
        return ResponseEntity.ok(super.createDataResponse(response,
                "token.refreshed", locale, HttpStatus.OK, username));
    }
}
