package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}