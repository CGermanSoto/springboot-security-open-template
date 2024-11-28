// src/main/java/com/spacecodee/springbootsecurityopentemplate/exceptions/user/RoleNotFoundException.java
package com.spacecodee.springbootsecurityopentemplate.exceptions.user;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BusinessException;

public class RoleNotFoundException extends BusinessException {
    public RoleNotFoundException(String messageKey, String locale) {
        super(messageKey, locale);
    }
}