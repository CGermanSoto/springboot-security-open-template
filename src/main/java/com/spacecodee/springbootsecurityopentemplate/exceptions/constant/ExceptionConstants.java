package com.spacecodee.springbootsecurityopentemplate.exceptions.constant;

public final class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String CANNOT_SAVE = "error.cannot.save";
    public static final String NO_CONTENT = "error.no.content";
    public static final String INVALID_CREDENTIALS = "error.invalid.credentials";
    // Add more constants...
}