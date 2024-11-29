package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}