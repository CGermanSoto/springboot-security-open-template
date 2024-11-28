package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class NoDisabledException extends BusinessException {
    public NoDisabledException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}