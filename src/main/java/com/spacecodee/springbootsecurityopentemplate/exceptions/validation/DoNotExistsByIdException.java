package com.spacecodee.springbootsecurityopentemplate.exceptions.validation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class DoNotExistsByIdException extends BusinessException {
    public DoNotExistsByIdException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}