package com.spacecodee.springbootsecurityopentemplate.service.security.user;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserSecurityService extends UserDetailsService {

    UserSecurityDTO findByUsername(String username);

    UserEntity findUserEntityByUsername(String username);

}
