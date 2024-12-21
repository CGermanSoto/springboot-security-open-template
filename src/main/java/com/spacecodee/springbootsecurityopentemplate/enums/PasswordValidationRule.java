package com.spacecodee.springbootsecurityopentemplate.enums;

import lombok.Getter;

@Getter
public enum PasswordValidationRule {
    MIN_LENGTH(8),
    MAX_LENGTH(32),
    MIN_SPECIAL_CHARS(1),
    MIN_DIGITS(1),
    MIN_UPPERCASE(1),
    MIN_LOWERCASE(1);

    private final int value;

    PasswordValidationRule(int value) {
        this.value = value;
    }

}