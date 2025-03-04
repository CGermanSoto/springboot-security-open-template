package com.spacecodee.springbootsecurityopentemplate.events.security.token;

import com.spacecodee.springbootsecurityopentemplate.data.record.TokenEvent;
import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.state.ITokenStateService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenEventListener {

    private final ITokenStateService tokenStateService;

    @EventListener
    public void handleTokenEvent(@NotNull TokenEvent event) {
        if ("VALIDATE".equals(event.operation())) {
            this.tokenStateService.updateTokenAccess(event.token(), event.reason());
        } else if ("EXPIRE".equals(event.operation())) {
            this.tokenStateService.updateTokenState(event.token(), TokenStateEnum.EXPIRED, event.reason());
        }
    }
}