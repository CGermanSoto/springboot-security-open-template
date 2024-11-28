package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}