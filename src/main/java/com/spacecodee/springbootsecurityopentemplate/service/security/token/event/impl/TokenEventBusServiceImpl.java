package com.spacecodee.springbootsecurityopentemplate.service.security.token.event.impl;

import com.spacecodee.springbootsecurityopentemplate.data.record.TokenEvent;
import com.spacecodee.springbootsecurityopentemplate.data.record.TokenStateChangeEvent;
import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.event.ITokenEventBusService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenEventBusServiceImpl implements ITokenEventBusService {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publishTokenEvent(String token, String operation, String reason) {
        eventPublisher.publishEvent(new TokenEvent(token, operation, reason));
    }

    @Override
    public void publishTokenStateChange(String token, TokenStateEnum state, String reason) {
        eventPublisher.publishEvent(new TokenStateChangeEvent(token, state, reason));

    }
}