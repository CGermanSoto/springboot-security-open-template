package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class InvalidPasswordException extends BaseException {
    public InvalidPasswordException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}