package com.spacecodee.springbootsecurityopentemplate.data.record;

public record TokenEvent(
        String token,
        String operation,
        String reason
) {
}