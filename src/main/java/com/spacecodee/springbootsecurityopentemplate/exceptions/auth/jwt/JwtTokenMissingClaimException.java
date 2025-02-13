package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenMissingClaimException extends BaseException {

    public JwtTokenMissingClaimException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
