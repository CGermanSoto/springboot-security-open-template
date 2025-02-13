package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenHeaderAlgException extends BaseException {

    public JwtTokenHeaderAlgException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
