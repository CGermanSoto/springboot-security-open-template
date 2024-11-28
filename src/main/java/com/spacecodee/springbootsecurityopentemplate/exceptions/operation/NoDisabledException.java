package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class NoDisabledException extends BaseException {
    public NoDisabledException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}