package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}