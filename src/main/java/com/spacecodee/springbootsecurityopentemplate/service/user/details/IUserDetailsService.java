package com.spacecodee.springbootsecurityopentemplate.service.user.details;

import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsDTO;

import java.util.Optional;

public interface IUserDetailsService {

    Optional<UserDetailsDTO> findByUsername(String username);
}
