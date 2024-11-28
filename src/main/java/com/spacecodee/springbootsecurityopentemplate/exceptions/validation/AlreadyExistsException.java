package com.spacecodee.springbootsecurityopentemplate.exceptions.validation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class AlreadyExistsException extends BusinessException {
    public AlreadyExistsException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}