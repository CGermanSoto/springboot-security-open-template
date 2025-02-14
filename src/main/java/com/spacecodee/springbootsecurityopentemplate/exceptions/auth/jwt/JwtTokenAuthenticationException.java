package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenAuthenticationException extends BaseException {

    public JwtTokenAuthenticationException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }
}
