package com.spacecodee.springbootsecurityopentemplate.service;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.JwtTokeUVO;

import java.util.Optional;

public interface IJwtTokenService {

    Optional<UserDetailsJwtTokenDTO> findByToken(String jwt, String lang);

    Optional<UserDetailsJwtTokenDTO> findByToken(String jwt);

    Optional<SecurityJwtTokenDTO> findBySecurityToken(String jwt);

    void save(UserDetailsJwtTokenDTO token);

    void save(SecurityJwtTokenDTO securityJwtTokenDTO);

    void save(JwtTokeUVO token);
}
