package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordException extends BaseException {
    public InvalidPasswordException(List<String> messages, String locale) {
        super(String.join(" ", messages), locale);
    }

    public InvalidPasswordException(String message, String locale) {
        super(message, locale);
    }
}