package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenHeaderValidationException extends BaseException {

    public JwtTokenHeaderValidationException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
