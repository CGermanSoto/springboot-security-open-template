package com.spacecodee.springbootsecurityopentemplate.service;

import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.ticklyspace.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.ticklyspace.data.vo.auth.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService {

    AuthenticationResponsePojo login(LoginUserVO request);

    boolean validateToken(String jwt);

    UserDetailsDTO findLoggedInUser();

    void logout(HttpServletRequest request);
}
