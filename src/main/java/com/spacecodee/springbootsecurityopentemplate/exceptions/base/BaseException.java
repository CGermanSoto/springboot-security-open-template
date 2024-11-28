package com.spacecodee.springbootsecurityopentemplate.exceptions.base;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final String messageKey;
    private final String locale;

    protected BaseException(String messageKey, String locale) {
        this.messageKey = messageKey;
        this.locale = locale;
    }
}