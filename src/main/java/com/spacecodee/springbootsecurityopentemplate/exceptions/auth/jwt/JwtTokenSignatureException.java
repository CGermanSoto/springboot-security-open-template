package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenSignatureException extends BaseException {

    public JwtTokenSignatureException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }
}
