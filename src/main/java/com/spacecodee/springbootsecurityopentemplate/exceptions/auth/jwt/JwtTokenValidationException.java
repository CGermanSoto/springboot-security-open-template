package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenValidationException extends BaseException {

    public JwtTokenValidationException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }

}
