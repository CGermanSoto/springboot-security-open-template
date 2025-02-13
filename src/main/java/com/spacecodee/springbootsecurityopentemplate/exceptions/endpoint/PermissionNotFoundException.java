package com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class PermissionNotFoundException extends BaseException {
    public PermissionNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public PermissionNotFoundException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }
}