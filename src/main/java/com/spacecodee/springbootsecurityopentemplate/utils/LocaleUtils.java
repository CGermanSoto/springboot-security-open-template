package com.spacecodee.springbootsecurityopentemplate.utils;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LocaleUtils {

    public String getCurrentLocale() {
        return LocaleContextHolder.getLocale().getLanguage();
    }
}
