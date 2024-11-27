package com.spacecodee.springbootsecurityopentemplate.controller.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.IAuthenticationController;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginUserVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationControllerImpl implements IAuthenticationController {

    private final IAuthenticationService authenticationService;
    private final MessageUtilComponent messageUtilComponent;

    @Override
    public ResponseEntity<ApiResponseDataPojo<Boolean>> validate(String locale, String jwt) {
        var apiResponse = new ApiResponseDataPojo<Boolean>();
        boolean isTokenValid = this.authenticationService.validateToken(locale, jwt);

        apiResponse.setData(isTokenValid);
        apiResponse.setHttpStatus(HttpStatus.OK);
        if (!isTokenValid) {
            apiResponse.setMessage(this.messageUtilComponent.getMessage("token.inValid", locale));
        }

        apiResponse.setMessage(this.messageUtilComponent.getMessage("token.valid", locale));
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> authenticate(String locale,
            LoginUserVO request) {
        var apiResponse = new ApiResponseDataPojo<AuthenticationResponsePojo>();
        var authenticationResponsePojo = this.authenticationService.login(locale, request);
        apiResponse.setData(authenticationResponsePojo);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("login.success", locale));
        apiResponse.setHttpStatus(HttpStatus.ACCEPTED);

        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<UserDetailsDTO>> profile(String locale) {
        var udUser = new ApiResponseDataPojo<UserDetailsDTO>();
        udUser.setData(this.authenticationService.findLoggedInUser(locale));
        udUser.setMessage(this.messageUtilComponent.getMessage("user.profile", locale));
        udUser.setHttpStatus(HttpStatus.OK);

        return new ResponseEntity<>(udUser, HttpStatus.valueOf(udUser.getStatus()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> logout(String locale, HttpServletRequest request) {
        var apiResponse = new ApiResponsePojo();

        this.authenticationService.logout(locale, request);

        apiResponse.setMessage(this.messageUtilComponent.getMessage("logout.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> refreshToken(
            String locale, HttpServletRequest request) {
        var apiResponse = new ApiResponseDataPojo<AuthenticationResponsePojo>();
        var refreshedToken = this.authenticationService.refreshToken(locale, request);

        apiResponse.setData(refreshedToken);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("token.refreshed", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
