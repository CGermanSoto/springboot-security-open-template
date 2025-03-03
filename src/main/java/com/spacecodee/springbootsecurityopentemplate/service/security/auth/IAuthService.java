package com.spacecodee.springbootsecurityopentemplate.service.security.auth;

import org.jetbrains.annotations.NotNull;

import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.AuthResponseDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface IAuthService {

    AuthResponseDTO login(@Valid LoginVO loginVO);

    AuthResponseDTO refreshToken(@NotNull String token);

    void logout(HttpServletRequest request);

    boolean validateToken(String token);

}