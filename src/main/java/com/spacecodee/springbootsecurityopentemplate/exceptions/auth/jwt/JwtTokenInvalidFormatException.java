package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenInvalidFormatException extends BaseException {

    public JwtTokenInvalidFormatException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}
