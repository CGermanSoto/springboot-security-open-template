package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class CannotSaveException extends BaseException {
    public CannotSaveException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}