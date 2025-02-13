package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtValidationException extends BaseException {

    public JwtValidationException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }

}
