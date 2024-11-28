package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class TokenNotFoundException extends BusinessException {
    public TokenNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}