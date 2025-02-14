package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class AccessDeniedException extends BaseException {

    public AccessDeniedException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}