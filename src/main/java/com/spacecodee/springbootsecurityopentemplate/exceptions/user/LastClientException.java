package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class LastClientException extends BaseException {
    public LastClientException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}