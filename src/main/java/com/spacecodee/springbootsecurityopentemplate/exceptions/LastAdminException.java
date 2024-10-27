package com.spacecodee.springbootsecurityopentemplate.exceptions;

public class LastAdminException extends RuntimeException {

    public LastAdminException() {
    }

    public LastAdminException(String message) {
        super(message);
    }

    public LastAdminException(String message, Throwable cause) {
        super(message, cause);
    }
}
