package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenNotFoundException extends BaseException {

    public JwtTokenNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }

}