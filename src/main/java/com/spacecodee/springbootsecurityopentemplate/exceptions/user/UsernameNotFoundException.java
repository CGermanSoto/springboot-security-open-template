package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class UsernameNotFoundException extends BaseException {
    public UsernameNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}