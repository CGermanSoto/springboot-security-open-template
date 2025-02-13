package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenInvalidException extends BaseException {

    public JwtTokenInvalidException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public JwtTokenInvalidException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}