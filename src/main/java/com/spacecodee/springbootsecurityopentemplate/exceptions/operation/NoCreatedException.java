package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class NoCreatedException extends BaseException {

    public NoCreatedException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public NoCreatedException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}