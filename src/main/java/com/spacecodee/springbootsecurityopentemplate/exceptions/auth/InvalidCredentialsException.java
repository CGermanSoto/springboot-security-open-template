package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class InvalidCredentialsException extends BusinessException {
    public InvalidCredentialsException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}