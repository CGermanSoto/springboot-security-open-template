package com.spacecodee.springbootsecurityopentemplate.service.user.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserDetailsService extends UserDetailsService {
    UserDetailsDTO findByUsername(String username);
}
