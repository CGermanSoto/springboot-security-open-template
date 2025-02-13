package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenUnexpectedException extends BaseException {

    public JwtTokenUnexpectedException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public JwtTokenUnexpectedException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}