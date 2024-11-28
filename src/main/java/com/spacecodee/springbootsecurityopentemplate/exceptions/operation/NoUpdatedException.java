package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class NoUpdatedException extends BusinessException {
    public NoUpdatedException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}