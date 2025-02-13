package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.ratelimit;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class RateLimitException extends BaseException {

    public RateLimitException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }

}
