package com.spacecodee.springbootsecurityopentemplate.controller.impl;

import com.spacecodee.ticklyspace.controller.IAuthenticationController;
import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.ticklyspace.data.pojo.ApiResponseDataPojo;
import com.spacecodee.ticklyspace.data.pojo.ApiResponsePojo;
import com.spacecodee.ticklyspace.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.ticklyspace.data.vo.auth.LoginUserVO;
import com.spacecodee.ticklyspace.language.MessageUtilComponent;
import com.spacecodee.ticklyspace.service.IAuthenticationService;
import com.spacecodee.ticklyspace.service.user.IUserService;
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
    private final IUserService userService;
    private final MessageUtilComponent messageUtilComponent;

    @Override
    public ResponseEntity<ApiResponseDataPojo<Boolean>> validate(String jwt, String lang) {
        var apiResponse = new ApiResponseDataPojo<Boolean>();
        boolean isTokenValid = this.authenticationService.validateToken(jwt);

        apiResponse.setData(isTokenValid);
        apiResponse.setHttpStatus(HttpStatus.OK);
        if (!isTokenValid) {
            apiResponse.setMessage(this.messageUtilComponent.getMessage("token.inValid", lang));
        }

        apiResponse.setMessage(this.messageUtilComponent.getMessage("token.valid", lang));
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> authenticate(LoginUserVO request, String lang) {
        var apiResponse = new ApiResponseDataPojo<AuthenticationResponsePojo>();
        var rsp = this.authenticationService.login(request);
        apiResponse.setData(rsp);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("login.success", lang));
        apiResponse.setHttpStatus(HttpStatus.ACCEPTED);

        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<UserDetailsDTO>> profile(String lang) {
        var udUser = new ApiResponseDataPojo<UserDetailsDTO>();
        udUser.setData(this.authenticationService.findLoggedInUser());
        udUser.setMessage(this.messageUtilComponent.getMessage("user.profile", lang));
        udUser.setHttpStatus(HttpStatus.OK);

        return new ResponseEntity<>(udUser, HttpStatus.valueOf(udUser.getStatus()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> logout(HttpServletRequest request, String lang) {
        var apiResponse = new ApiResponsePojo();

        this.authenticationService.logout(request);

        apiResponse.setMessage(this.messageUtilComponent.getMessage("logout.success", lang));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }
}
