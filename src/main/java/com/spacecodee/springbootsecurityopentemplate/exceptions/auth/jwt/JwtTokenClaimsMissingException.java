package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class JwtTokenClaimsMissingException extends BaseException {

    public JwtTokenClaimsMissingException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public JwtTokenClaimsMissingException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

}