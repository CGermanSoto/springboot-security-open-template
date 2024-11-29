package com.spacecodee.springbootsecurityopentemplate.service.security;

import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.JwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.jwt.JwtTokeUVO;

import java.util.Optional;

public interface IJwtTokenService {

    boolean existsByJwtTokenToken(String lang, String jwt);

    String getTokenByUsername(String username);

    Optional<JwtTokenDTO> findTokenByUsername(String jwt);

    SecurityJwtTokenDTO findBySecurityToken(String locale, String jwt);

    void save(UserDetailsJwtTokenDTO token);

    void save(SecurityJwtTokenDTO securityJwtTokenDTO);

    void save(JwtTokeUVO token);

    void deleteByToken(String locale, String token);

    void deleteByUserId(String locale, Integer userId);
}
