package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenHeaderALGException extends BaseException {

    public JwtTokenHeaderALGException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
