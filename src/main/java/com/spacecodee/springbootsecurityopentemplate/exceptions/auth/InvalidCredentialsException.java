package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class InvalidCredentialsException extends BaseException {

    public InvalidCredentialsException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}