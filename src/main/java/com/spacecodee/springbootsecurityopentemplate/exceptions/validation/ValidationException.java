// ValidationException.java
package com.spacecodee.springbootsecurityopentemplate.exceptions.validation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class ValidationException extends BusinessException {
    public ValidationException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}