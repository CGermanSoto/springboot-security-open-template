package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenExpiredException extends BaseException {

    public JwtTokenExpiredException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public JwtTokenExpiredException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}