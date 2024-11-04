package com.spacecodee.springbootsecurityopentemplate.service.user.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;

import java.util.Optional;

public interface IUserDetailsService {

    UserDetailsDTO findOneByUsername(String locale, String username);

    Optional<UserDetailsDTO> findByUsername(String username);
}
