package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenHeaderALgException extends BaseException {

    public JwtTokenHeaderALgException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
