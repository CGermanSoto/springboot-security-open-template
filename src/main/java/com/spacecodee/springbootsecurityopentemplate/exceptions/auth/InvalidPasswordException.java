package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}