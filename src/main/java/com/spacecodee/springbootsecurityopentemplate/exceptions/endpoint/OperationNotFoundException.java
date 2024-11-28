package com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class OperationNotFoundException extends BaseException {
    public OperationNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}