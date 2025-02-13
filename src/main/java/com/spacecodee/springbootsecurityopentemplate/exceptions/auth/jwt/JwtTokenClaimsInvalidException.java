package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenClaimsInvalidException extends BaseException {

    public JwtTokenClaimsInvalidException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public JwtTokenClaimsInvalidException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}