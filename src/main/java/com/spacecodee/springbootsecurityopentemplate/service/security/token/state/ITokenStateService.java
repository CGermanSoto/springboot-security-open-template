package com.spacecodee.springbootsecurityopentemplate.service.security.token.state;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;

public interface ITokenStateService {

    void updateTokenState(String token, TokenStateEnum state, String reason);

    void updateTokenAccess(String token, String operation);

}
