package com.spacecodee.springbootsecurityopentemplate.data.record;

import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;

public record TokenStateChangeEvent(
        String token,
        TokenStateEnum state,
        String reason
) {
}