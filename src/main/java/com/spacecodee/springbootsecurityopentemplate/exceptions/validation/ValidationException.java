// ValidationException.java
package com.spacecodee.springbootsecurityopentemplate.exceptions.validation;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class ValidationException extends BaseException {
    public ValidationException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}