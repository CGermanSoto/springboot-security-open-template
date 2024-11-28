package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class NoCreatedException extends BusinessException {
    public NoCreatedException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}