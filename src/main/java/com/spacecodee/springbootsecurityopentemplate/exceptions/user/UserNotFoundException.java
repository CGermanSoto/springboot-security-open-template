package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}