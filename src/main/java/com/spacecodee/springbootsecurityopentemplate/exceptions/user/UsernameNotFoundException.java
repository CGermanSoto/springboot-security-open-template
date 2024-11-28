package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class UsernameNotFoundException extends BusinessException {
    public UsernameNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}