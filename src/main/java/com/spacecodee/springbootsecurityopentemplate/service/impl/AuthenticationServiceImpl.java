package com.spacecodee.springbootsecurityopentemplate.service.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.LoginUserVO;
import com.spacecodee.springbootsecurityopentemplate.service.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    @Override
    public AuthenticationResponsePojo login(LoginUserVO request) {
        return null;
    }

    @Override
    public boolean validateToken(String jwt) {
        return false;
    }

    @Override
    public UserDetailsDTO findLoggedInUser() {
        return null;
    }

    @Override
    public void logout(HttpServletRequest request) {
    }
}
