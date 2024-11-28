package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class TokenNotFoundException extends BaseException {
    public TokenNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}