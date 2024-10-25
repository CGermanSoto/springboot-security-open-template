package com.spacecodee.springbootsecurityopentemplate.controller;

import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.ticklyspace.data.pojo.ApiResponseDataPojo;
import com.spacecodee.ticklyspace.data.pojo.ApiResponsePojo;
import com.spacecodee.ticklyspace.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.ticklyspace.data.vo.auth.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IAuthenticationController {

    @GetMapping("/validate-token")
    ResponseEntity<ApiResponseDataPojo<Boolean>> validate(@RequestParam String jwt, @RequestParam String lang);

    @PostMapping("/authenticate")
    ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> authenticate(@RequestBody @Valid LoginUserVO request, @RequestParam String lang);

    @GetMapping("/profile")
    ResponseEntity<ApiResponseDataPojo<UserDetailsDTO>> profile(@RequestParam String lang);

    @PostMapping("/logout")
    ResponseEntity<ApiResponsePojo> logout(HttpServletRequest request, @RequestParam String lang);
}
