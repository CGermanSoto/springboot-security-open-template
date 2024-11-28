package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class LastAdminException extends BaseException {
    public LastAdminException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}