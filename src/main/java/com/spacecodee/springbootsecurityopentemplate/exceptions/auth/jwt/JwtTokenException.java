package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenException extends BaseException {

    public JwtTokenException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public JwtTokenException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }
}