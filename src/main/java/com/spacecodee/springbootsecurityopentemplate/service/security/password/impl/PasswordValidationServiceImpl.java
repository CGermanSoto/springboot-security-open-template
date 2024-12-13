package com.spacecodee.springbootsecurityopentemplate.service.security.password.impl;

import com.spacecodee.springbootsecurityopentemplate.enums.PasswordValidationRule;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.service.security.password.IPasswordValidationService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PasswordValidationServiceImpl implements IPasswordValidationService {
    private final ExceptionShortComponent exceptionShortComponent;
    private static final Pattern SPECIAL_CHARS = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern DIGITS = Pattern.compile("[0-9]");

    @Override
    public void validatePassword(@NotNull String password, @NotNull String locale) {
        if (password.isBlank()) {
            throw this.exceptionShortComponent.invalidPasswordComplexityException("validation.password.required",
                    locale);
        }

        Map<String, Boolean> validations = this.validatePasswordRules(password);
        List<String> failures = new ArrayList<>();

        if (!validations.get("length")) {
            failures.add("validation.password.length");
        }

        if (!validations.get("uppercase")) {
            failures.add("validation.password.uppercase");
        }

        if (!validations.get("lowercase")) {
            failures.add("validation.password.lowercase");
        }

        if (!validations.get("digit")) {
            failures.add("validation.password.digit");
        }

        if (!validations.get("special")) {
            failures.add("validation.password.special");
        }

        if (!failures.isEmpty()) {
            throw this.exceptionShortComponent.invalidPasswordComplexityException("validation.password.multiple.errors",
                    locale);
        }
    }

    @Override
    public Map<String, Boolean> validatePasswordRules(@NotNull String password) {
        Map<String, Boolean> validations = new HashMap<>();

        validations.put("length",
                password.length() >= PasswordValidationRule.MIN_LENGTH.getValue() &&
                        password.length() <= PasswordValidationRule.MAX_LENGTH.getValue());

        validations.put("uppercase",
                countMatches(UPPERCASE.matcher(password)) >= PasswordValidationRule.MIN_UPPERCASE.getValue());

        validations.put("lowercase",
                countMatches(LOWERCASE.matcher(password)) >= PasswordValidationRule.MIN_LOWERCASE.getValue());

        validations.put("digit",
                countMatches(DIGITS.matcher(password)) >= PasswordValidationRule.MIN_DIGITS.getValue());

        validations.put("special",
                countMatches(SPECIAL_CHARS.matcher(password)) >= PasswordValidationRule.MIN_SPECIAL_CHARS.getValue());

        return validations;
    }

    private int countMatches(@NotNull Matcher matcher) {
        int count = 0;
        while (matcher.find())
            count++;
        return count;
    }
}