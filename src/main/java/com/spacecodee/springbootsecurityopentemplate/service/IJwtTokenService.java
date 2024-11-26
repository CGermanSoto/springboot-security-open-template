package com.spacecodee.springbootsecurityopentemplate.service;

import com.spacecodee.springbootsecurityopentemplate.data.dto.JwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.JwtTokeUVO;

import java.util.Optional;

public interface IJwtTokenService {

    boolean existsByJwtTokenToken(String lang, String jwt);

    Optional<JwtTokenDTO> findTokenByUsername(String jwt);

    SecurityJwtTokenDTO findBySecurityToken(String jwt);

    void save(UserDetailsJwtTokenDTO token);

    void save(SecurityJwtTokenDTO securityJwtTokenDTO);

    void save(JwtTokeUVO token);

    void deleteByToken(String lang, String token);
}
