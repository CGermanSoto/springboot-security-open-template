package com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class ModuleNotFoundException extends BaseException {
    public ModuleNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}