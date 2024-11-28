package com.spacecodee.springbootsecurityopentemplate.exceptions.validation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class PasswordDoNotMatchException extends BaseException {
    public PasswordDoNotMatchException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}