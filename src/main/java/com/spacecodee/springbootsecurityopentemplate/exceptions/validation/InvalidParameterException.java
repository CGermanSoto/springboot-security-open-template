package com.spacecodee.springbootsecurityopentemplate.exceptions.validation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class InvalidParameterException extends BusinessException {
    public InvalidParameterException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}