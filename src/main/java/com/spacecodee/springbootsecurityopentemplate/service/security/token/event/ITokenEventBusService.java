package com.spacecodee.springbootsecurityopentemplate.service.security.token.event;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;

public interface ITokenEventBusService {

    void publishTokenStateChange(String token, TokenStateEnum state, String reason);

}