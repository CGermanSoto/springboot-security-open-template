package com.spacecodee.springbootsecurityopentemplate.exceptions.auth;

import com.spacecodee.springbootsecurityopentemplate.exceptions.base.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordComplexityException extends BaseException {

    public InvalidPasswordComplexityException(List<String> messages, String locale) {
        super(String.join("|", messages), locale);
        log.debug("Creating exception with messages: {}", messages);
    }

    public InvalidPasswordComplexityException(String messageKey, String locale) {
        super(messageKey, locale);
        log.debug("Creating exception with message key: {}", messageKey);
    }
}