package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtExpiredTokenException extends BaseException {

    public JwtExpiredTokenException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
