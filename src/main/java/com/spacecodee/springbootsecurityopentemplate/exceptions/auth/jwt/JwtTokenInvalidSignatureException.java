package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenInvalidSignatureException extends BaseException {

    public JwtTokenInvalidSignatureException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
