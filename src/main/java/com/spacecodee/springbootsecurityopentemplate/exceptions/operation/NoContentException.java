package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class NoContentException extends BusinessException {
    public NoContentException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}