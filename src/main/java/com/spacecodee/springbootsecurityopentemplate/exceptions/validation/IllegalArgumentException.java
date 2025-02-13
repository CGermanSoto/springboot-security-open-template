package com.spacecodee.springbootsecurityopentemplate.exceptions.validation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class IllegalArgumentException extends BaseException {

    public IllegalArgumentException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public IllegalArgumentException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}
