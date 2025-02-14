package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class NoDeletedException extends BaseException {

    public NoDeletedException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public NoDeletedException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}