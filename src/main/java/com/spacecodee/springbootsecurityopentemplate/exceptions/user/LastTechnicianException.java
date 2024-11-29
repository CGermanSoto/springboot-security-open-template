package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class LastTechnicianException extends BaseException {
    public LastTechnicianException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}