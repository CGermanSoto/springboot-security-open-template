package com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class PermissionNotFoundException extends BusinessException {
    public PermissionNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}