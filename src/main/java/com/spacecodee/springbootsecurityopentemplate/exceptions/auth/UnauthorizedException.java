package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}