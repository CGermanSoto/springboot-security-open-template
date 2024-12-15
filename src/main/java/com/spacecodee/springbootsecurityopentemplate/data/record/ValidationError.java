package com.spacecodee.springbootsecurityopentemplate.data.record;

public record ValidationError(
        String field,
        Object rejectedValue,
        String message,
        Object[] parameters
) {
}
