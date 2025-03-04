package com.spacecodee.springbootsecurityopentemplate.cache;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;

public interface ITokenCacheService {

    void cacheToken(String token, JwtTokenEntity tokenEntity);

    JwtTokenEntity getFromCache(String token);

    void removeFromCache(String token);

    void clearCache();

    void cacheTokenState(String token, TokenStateEnum state);

    TokenStateEnum getTokenStateFromCache(String token);

    void cacheUserDetails(String username, UserSecurityDTO userDetails);

    UserSecurityDTO getUserDetailsFromCache(String username);

}
