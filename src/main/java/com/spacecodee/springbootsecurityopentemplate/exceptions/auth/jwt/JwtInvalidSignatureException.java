package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtInvalidSignatureException extends BaseException {

    public JwtInvalidSignatureException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
