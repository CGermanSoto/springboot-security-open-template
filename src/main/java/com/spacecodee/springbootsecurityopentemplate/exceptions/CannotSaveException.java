package com.spacecodee.springbootsecurityopentemplate.exceptions;

public class CannotSaveException extends RuntimeException {

    public CannotSaveException() {
    }

    public CannotSaveException(String message) {
        super(message);
    }

    public CannotSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
