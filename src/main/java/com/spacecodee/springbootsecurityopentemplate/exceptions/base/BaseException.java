package com.spacecodee.springbootsecurityopentemplate.exceptions.base;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final String messageKey;
    private final String locale;
    private final transient Object[] parameters;

    protected BaseException(String messageKey, String locale, Object... parameters) {
        super(messageKey);
        this.messageKey = messageKey;
        this.locale = locale;
        this.parameters = parameters;
    }
}