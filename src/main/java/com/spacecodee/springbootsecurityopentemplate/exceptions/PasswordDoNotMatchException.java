package com.spacecodee.springbootsecurityopentemplate.exceptions;

public class PasswordDoNotMatchException extends RuntimeException {

    public PasswordDoNotMatchException(String message) {
        super(message);
    }

    public PasswordDoNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
