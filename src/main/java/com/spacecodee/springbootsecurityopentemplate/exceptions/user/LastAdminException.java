package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class LastAdminException extends BusinessException {
    public LastAdminException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}