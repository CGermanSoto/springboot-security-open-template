package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenHeaderFormatException extends BaseException {

    public JwtTokenHeaderFormatException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
