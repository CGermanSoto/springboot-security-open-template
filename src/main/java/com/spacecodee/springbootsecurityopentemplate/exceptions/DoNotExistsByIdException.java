package com.spacecodee.springbootsecurityopentemplate.exceptions;

public class DoNotExistsByIdException extends RuntimeException {

    public DoNotExistsByIdException(String message) {
        super(message);
    }

    public DoNotExistsByIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
