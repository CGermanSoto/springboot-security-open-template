// UsernameAlreadyExistsException.java
package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;

public class UsernameAlreadyExistsException extends BaseException {
    public UsernameAlreadyExistsException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}