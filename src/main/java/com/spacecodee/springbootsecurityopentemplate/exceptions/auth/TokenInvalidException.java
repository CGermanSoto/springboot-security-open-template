package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class TokenInvalidException extends BaseException {
    public TokenInvalidException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}