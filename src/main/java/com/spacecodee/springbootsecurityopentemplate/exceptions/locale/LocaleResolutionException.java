package com.spacecodee.springbootsecurityopentemplate.exceptions.locale;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class LocaleResolutionException extends BaseException {

    public LocaleResolutionException(String messageKey, String locale, Object... parameters) {
        super(messageKey, locale, parameters);
    }

    public LocaleResolutionException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}