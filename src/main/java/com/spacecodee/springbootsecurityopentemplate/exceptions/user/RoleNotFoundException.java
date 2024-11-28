package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}