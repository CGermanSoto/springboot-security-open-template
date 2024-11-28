package com.spacecodee.springbootsecurityopentemplate.exceptions.base;

public class ObjectNotFoundException extends BusinessException {
    public ObjectNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}