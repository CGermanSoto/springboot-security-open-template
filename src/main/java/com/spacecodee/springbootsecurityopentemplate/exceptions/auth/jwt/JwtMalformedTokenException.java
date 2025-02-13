package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtMalformedTokenException extends BaseException {

    public JwtMalformedTokenException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
