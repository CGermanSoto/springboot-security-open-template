package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class NoUpdatedException extends BaseException {
    public NoUpdatedException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}