package com.spacecodee.springbootsecurityopentemplate.exceptions;

public class NoDeletedException extends RuntimeException {

    public NoDeletedException() {
    }

    public NoDeletedException(String message) {
        super(message);
    }

    public NoDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
