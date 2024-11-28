package com.spacecodee.springbootsecurityopentemplate.exceptions.operation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class NoContentException extends BaseException {
    public NoContentException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}