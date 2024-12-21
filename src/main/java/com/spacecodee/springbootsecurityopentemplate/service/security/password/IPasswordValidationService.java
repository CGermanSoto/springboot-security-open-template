package com.spacecodee.springbootsecurityopentemplate.service.security.password;

import java.util.Map;

public interface IPasswordValidationService {
    void validatePassword(String password, String locale);

    Map<String, Boolean> validatePasswordRules(String password);
}