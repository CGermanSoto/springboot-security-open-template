package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class TokenUnexpectedException extends BaseException {
    public TokenUnexpectedException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}