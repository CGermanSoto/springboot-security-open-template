package com.spacecodee.springbootsecurityopentemplate.exceptions.base;

public class ObjectNotFoundException extends BaseException {
    public ObjectNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}