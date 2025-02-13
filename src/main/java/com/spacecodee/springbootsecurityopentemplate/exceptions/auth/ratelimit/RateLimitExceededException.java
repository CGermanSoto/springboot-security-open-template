package com.spacecodee.springbootsecurityopentemplate.exceptions.auth.ratelimit;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitExceededException extends BaseException {

    public RateLimitExceededException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public RateLimitExceededException(String messageKey, String locale, Object... args) {
        super(messageKey, locale, args);
    }
}
