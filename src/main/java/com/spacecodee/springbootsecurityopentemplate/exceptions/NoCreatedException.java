package com.spacecodee.springbootsecurityopentemplate.exceptions;

public class NoCreatedException extends RuntimeException {

    public NoCreatedException() {
    }

    public NoCreatedException(String message) {
        super(message);
    }

    public NoCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
