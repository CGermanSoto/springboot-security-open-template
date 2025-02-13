package com.spacecodee.springbootsecurityopentemplate.service.security.token.event;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;

public interface ITokenEventBusService {

    void publishTokenEvent(String token, String operation, String reason);

    void publishTokenStateChange(String token, TokenStateEnum state, String reason);

}