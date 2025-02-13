package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtHeaderValidationException extends BaseException {

    public JwtHeaderValidationException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
