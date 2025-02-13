package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenHeaderDecodeException extends BaseException {

    public JwtTokenHeaderDecodeException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
