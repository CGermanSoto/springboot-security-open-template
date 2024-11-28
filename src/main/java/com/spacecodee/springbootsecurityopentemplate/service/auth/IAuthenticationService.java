package com.spacecodee.springbootsecurityopentemplate.service.auth;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService {

    AuthenticationResponsePojo login(String locale, LoginUserVO request);

    boolean validateToken(String locale, String jwt);

    UserDetailsDTO findLoggedInUser(String locale);

    void logout(String locale, HttpServletRequest request);

    AuthenticationResponsePojo refreshToken(String locale, HttpServletRequest request);
}
