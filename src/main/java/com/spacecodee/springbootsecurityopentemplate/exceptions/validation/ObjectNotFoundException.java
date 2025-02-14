package com.spacecodee.springbootsecurityopentemplate.exceptions.validation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class ObjectNotFoundException extends BaseException {

    public ObjectNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public ObjectNotFoundException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }
}