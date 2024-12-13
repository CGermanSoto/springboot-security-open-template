package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordComplexityException extends BaseException {
    public InvalidPasswordComplexityException(String messageKey, String locale) {
        super(messageKey, locale);
    }

    public InvalidPasswordComplexityException(List<String> messages, String locale) {
        super(String.join(" ", messages), locale);
    }
}