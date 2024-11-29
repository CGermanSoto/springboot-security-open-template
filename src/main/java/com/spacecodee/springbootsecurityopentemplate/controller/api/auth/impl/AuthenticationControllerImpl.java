package com.spacecodee.springbootsecurityopentemplate.controller.api.auth.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.auth.IAuthenticationController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.auth.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginUserVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.auth.IAuthenticationService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtService;
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
    private final IJwtService jwtService;
    private final IAuthenticationService authenticationService;

    public AuthenticationControllerImpl(MessageUtilComponent messageUtilComponent,
                                        IJwtService jwtService,
                                        IAuthenticationService authenticationService) {
        super(messageUtilComponent);
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Boolean>> validate(String locale, HttpServletRequest request) {
        var jwt = this.jwtService.extractJwtFromRequest(request);
        if (jwt == null) {
            return ResponseEntity.badRequest()
                    .body(super.createDataResponse(false, "token.missing", locale, HttpStatus.BAD_REQUEST));
        }

        boolean isTokenValid = this.authenticationService.validateToken(locale, jwt);
        return ResponseEntity.ok(super.createDataResponse(isTokenValid,
                isTokenValid ? "token.valid" : "token.inValid", locale, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> authenticate(String locale,
                                                                                        LoginUserVO request) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(super.createDataResponse(
                        this.authenticationService.login(locale, request),
                        "login.success",
                        locale,
                        HttpStatus.ACCEPTED));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<UserDetailsDTO>> profile(String locale) {
        return ResponseEntity.ok(super.createDataResponse(
                this.authenticationService.findLoggedInUser(locale),
                "user.profile",
                locale,
                HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> logout(String locale, HttpServletRequest request) {
        this.authenticationService.logout(locale, request);
        return ResponseEntity.ok(super.createResponse("logout.success", locale, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> refreshToken(
            String locale, HttpServletRequest request) {
        return ResponseEntity.ok(super.createDataResponse(
                this.authenticationService.refreshToken(locale, request),
                "token.refreshed",
                locale,
                HttpStatus.OK));
    }
}
