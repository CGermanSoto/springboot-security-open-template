package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class NoDeletedException extends BusinessException {
    public NoDeletedException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}