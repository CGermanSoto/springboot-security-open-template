// UsernameAlreadyExistsException.java
package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class UsernameAlreadyExistsException extends BusinessException {
    public UsernameAlreadyExistsException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}