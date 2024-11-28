package com.spacecodee.springbootsecurityopentemplate.utils;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

public class AppUtils {

    private AppUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean validatePassword(String password, String repeatedPassword) {
        return StringUtils.hasText(password) && StringUtils.hasText(repeatedPassword);
    }

    public static RoleEnum getRoleEnum(String roleName) {
        return RoleEnum.valueOf(roleName);
    }

    @Contract(value = "_, null -> true", pure = true)
    public static boolean comparePasswords(@NotNull String password, String repeatPassword) {
        return !password.equals(repeatPassword);
    }
}
