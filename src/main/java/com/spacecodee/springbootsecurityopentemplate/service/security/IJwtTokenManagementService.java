package com.spacecodee.springbootsecurityopentemplate.service.security;

import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.jwt.JwtTokeUVO;

public interface IJwtTokenManagementService {

    void saveToken(JwtTokeUVO token);

    void invalidateToken(String locale, String token);

    void invalidateUserTokens(String locale, Integer userId);

    String findActiveTokenByUsername(String username);

    boolean existsToken(String locale, String token);

    SecurityJwtTokenDTO getTokenDetails(String locale, String token);
}
