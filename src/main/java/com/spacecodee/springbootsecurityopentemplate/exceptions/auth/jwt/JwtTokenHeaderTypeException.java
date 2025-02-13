package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenHeaderTypeException extends BaseException {

    public JwtTokenHeaderTypeException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
