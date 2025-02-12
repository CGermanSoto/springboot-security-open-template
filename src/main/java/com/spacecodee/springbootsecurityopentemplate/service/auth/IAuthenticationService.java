package com.spacecodee.springbootsecurityopentemplate.service.auth;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.common.auth.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginVO;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService {

    AuthenticationResponsePojo login(String locale, LoginVO request);

    boolean validateToken(String locale, String jwt);

    UserSecurityDTO findLoggedInUser(String locale);

    void logout(String locale, HttpServletRequest request);

    AuthenticationResponsePojo refreshToken(String locale, HttpServletRequest request);
}
