package com.spacecodee.springbootsecurityopentemplate.service.security.token;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.UpdateJwtTokenVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;

public interface IJwtTokenSecurityService {

    void updateTokenToRefresh(UpdateJwtTokenVO token);

    JwtTokenEntity findByToken(String token);

    JwtTokenEntity handleExistingToken(String username, boolean includeExpired);

    JwtTokenEntity createNewTokenInLogin(UserSecurityDTO userSecurityDTO, UserEntity user);

    JwtTokenEntity refreshExistingTokenOnLogin(UserSecurityDTO userDetails, JwtTokenEntity existingToken);

    int revokeAllUserTokens(String username, String reason);
}
