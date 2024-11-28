package com.spacecodee.springbootsecurityopentemplate.exceptions.validation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class PasswordDoNotMatchException extends BusinessException {
    public PasswordDoNotMatchException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}