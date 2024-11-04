package com.spacecodee.springbootsecurityopentemplate.service.user.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserDetailsService extends UserDetailsService {
    Optional<UserDetailsDTO> findByUsername(String username);
}
