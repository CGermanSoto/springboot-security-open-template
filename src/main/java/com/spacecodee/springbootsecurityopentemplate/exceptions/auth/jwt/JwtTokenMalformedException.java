package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenMalformedException extends BaseException {

    public JwtTokenMalformedException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
