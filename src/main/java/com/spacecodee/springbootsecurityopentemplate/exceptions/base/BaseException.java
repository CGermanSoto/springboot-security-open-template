package com.spacecodee.springbootsecurityopentemplate.exceptions.base;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public abstract class BaseException extends RuntimeException {
    //private final String messageKey;
    private final String locale;

    protected BaseException(String messageKey, String locale) {
        super(messageKey);
        this.locale = locale;

        log.debug("Creating base exception with message: {}", messageKey);
    }
}