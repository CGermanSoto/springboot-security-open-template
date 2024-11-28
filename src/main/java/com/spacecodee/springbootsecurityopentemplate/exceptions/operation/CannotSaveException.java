package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class CannotSaveException extends BusinessException {
    public CannotSaveException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}