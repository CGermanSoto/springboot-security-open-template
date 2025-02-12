package com.spacecodee.springbootsecurityopentemplate.service.core.user.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserDetailsService extends UserDetailsService {
    UserSecurityDTO findByUsername(String locale, String username);
}
