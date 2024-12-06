package com.spacecodee.springbootsecurityopentemplate.controller.api.auth;

import com.spacecodee.springbootsecurityopentemplate.data.common.auth.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IAuthenticationController {

    @GetMapping("/validate-token")
    ResponseEntity<ApiResponseDataPojo<Boolean>> validate(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            HttpServletRequest request);

    @PostMapping("/authenticate")
    ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> authenticate(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @RequestBody @Valid LoginUserVO request);

    @GetMapping("/profile")
    ResponseEntity<ApiResponseDataPojo<UserDetailsDTO>> profile(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);

    @PostMapping("/logout")
    ResponseEntity<ApiResponsePojo> logout(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            HttpServletRequest request);

    @PutMapping("/refresh-token")
    ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> refreshToken(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            HttpServletRequest request);
}
