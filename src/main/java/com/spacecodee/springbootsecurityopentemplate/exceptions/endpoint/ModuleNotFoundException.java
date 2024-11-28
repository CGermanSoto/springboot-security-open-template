package com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class ModuleNotFoundException extends BusinessException {
    public ModuleNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}