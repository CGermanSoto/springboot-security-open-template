package com.spacecodee.springbootsecurityopentemplate.constants;

public final class ValidationConstants {
    private ValidationConstants() {
        throw new IllegalStateException("Utility class");
    }

    // Field Size Constants
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 32;
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 100;

    // Validation Message Keys
    public static final class Messages {
        private Messages() {
            throw new IllegalStateException("Utility class");
        }

        // Base validation
        public static final String USERNAME_SIZE = "validation.user.username.size";
        public static final String FULLNAME_SIZE = "validation.user.fullname.size";
        public static final String LASTNAME_SIZE = "validation.user.lastname.size";

        // Password validation
        public static final String PASSWORD_SIZE = "validation.user.password.size";
        public static final String REPEAT_PASSWORD_SIZE = "validation.user.repeat.password.size";
        public static final String PASSWORD_MATCH = "validation.password.match";

        // Other validations
        public static final String EMAIL_FORMAT = "validation.field.email";
        public static final String USERNAME_UNIQUE = "validation.username.unique";
        public static final String LOGIN_CREDENTIALS = "validation.login.credentials";
    }
}