package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class LastDeveloperException extends BaseException {
    public LastDeveloperException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}