package com.spacecodee.springbootsecurityopentemplate.utils;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.InvalidPasswordException;
import org.springframework.util.StringUtils;

public class AppUtils {

    private AppUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void validatePassword(String password, String repeatedPassword, String locale) {
        if (!StringUtils.hasText(password) || !StringUtils.hasText(repeatedPassword)) {
            throw new InvalidPasswordException("Passwords do not match");
        }

        if (!password.equals(repeatedPassword)) {
            throw new InvalidPasswordException("Passwords do not match");
        }
    }

    public static RoleEnum getRoleEnum(String roleName) {
        return RoleEnum.valueOf(roleName);
    }
}
