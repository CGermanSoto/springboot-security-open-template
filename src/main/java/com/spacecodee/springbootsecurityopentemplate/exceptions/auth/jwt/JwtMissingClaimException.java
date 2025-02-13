package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtMissingClaimException extends BaseException {

    public JwtMissingClaimException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
