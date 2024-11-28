package com.spacecodee.springbootsecurityopentemplate.exceptions.validation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class DoNotExistsByIdException extends BaseException {
    public DoNotExistsByIdException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}