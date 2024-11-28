package com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class OperationNotFoundException extends BusinessException {
    public OperationNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}