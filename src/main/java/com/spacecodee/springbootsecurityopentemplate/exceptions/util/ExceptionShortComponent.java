package com.spacecodee.springbootsecurityopentemplate.exceptions.util;

import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.*;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenInvalidException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.jwt.JwtTokenUnexpectedException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.ratelimit.RateLimitException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.*;
import com.spacecodee.springbootsecurityopentemplate.exceptions.user.UserNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.user.UsernameNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.utils.LocaleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExceptionShortComponent {

    private final LocaleUtils localeUtils;

    public Exception exception(String messageWithoutKey) {
        return new Exception(messageWithoutKey);
    }

    public AccessDeniedException accessDeniedException(String messageKey) {
        return new AccessDeniedException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public UnauthorizedException unauthorizedException(String messageKey) {
        return new UnauthorizedException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public UserNotFoundException userNotFoundException(String messageKey, String value) {
        return new UserNotFoundException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public RateLimitException rateLimitException(String messageKey, String value) {
        return new RateLimitException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public JwtTokenInvalidException tokenInvalidException(String messageKey) {
        return new JwtTokenInvalidException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public JwtTokenInvalidException tokenInvalidException(String messageKey, String value) {
        return new JwtTokenInvalidException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public JwtTokenExpiredException tokenExpiredException(String messageKey) {
        return new JwtTokenExpiredException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public JwtTokenExpiredException tokenExpiredException(String messageKey, String value) {
        return new JwtTokenExpiredException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public JwtTokenUnexpectedException tokenUnexpectedException(String messageKey) {
        return new JwtTokenUnexpectedException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public JwtTokenUnexpectedException tokenUnexpectedException(String messageKey, String value) {
        return new JwtTokenUnexpectedException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public JwtTokenNotFoundException tokenNotFoundException(String messageKey) {
        return new JwtTokenNotFoundException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public NoDeletedException noDeletedException(String messageKey) {
        return new NoDeletedException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public NoDeletedException noDeletedException(String messageKey, String value) {
        return new NoDeletedException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public NoDeletedException noDeletedException(String messageKey, String value0, String value1) {
        return new NoDeletedException(messageKey, this.localeUtils.getCurrentLocale(), value0, value1);
    }

    public AlreadyExistsException alreadyExistsException(String messageKey) {
        return new AlreadyExistsException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public AlreadyExistsException alreadyExistsException(String messageKey, String value) {
        return new AlreadyExistsException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public AlreadyExistsException alreadyExistsException(String messageKey, String value0, String value1) {
        return new AlreadyExistsException(messageKey, this.localeUtils.getCurrentLocale(), value0, value1);
    }

    public UsernameNotFoundException usernameNotFoundException(String messageKey) {
        return new UsernameNotFoundException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public CannotSaveException cannotSaveException(String messageKey) {
        return new CannotSaveException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public ObjectNotFoundException objectNotFoundException(String messageKey) {
        return new ObjectNotFoundException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public ObjectNotFoundException objectNotFoundException(String messageKey, String value) {
        return new ObjectNotFoundException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public ObjectNotFoundException objectNotFoundException(String messageKey, String value0, String value1) {
        return new ObjectNotFoundException(messageKey, this.localeUtils.getCurrentLocale(), value0, value1);
    }

    public NoContentException noContentException(String messageKey) {
        return new NoContentException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public NoCreatedException noCreatedException(String messageKey) {
        return new NoCreatedException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public NoCreatedException noCreatedException(String messageKey, String value) {
        return new NoCreatedException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public NoCreatedException noCreatedException(String messageKey, String value0, String value1) {
        return new NoCreatedException(messageKey, this.localeUtils.getCurrentLocale(), value0, value1);
    }

    public InvalidParameterException invalidParameterException(String messageKey) {
        return new InvalidParameterException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public InvalidParameterException invalidParameterException(String messageKey, String value) {
        return new InvalidParameterException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public InvalidParameterException invalidParameterException(String messageKey, String value0, String value1) {
        return new InvalidParameterException(messageKey, this.localeUtils.getCurrentLocale(), value0, value1);
    }

    public NoUpdatedException noUpdatedException(String messageKey) {
        return new NoUpdatedException(messageKey, this.localeUtils.getCurrentLocale());
    }

    public NoUpdatedException noUpdatedException(String messageKey, String value) {
        return new NoUpdatedException(messageKey, this.localeUtils.getCurrentLocale(), value);
    }

    public NoUpdatedException noUpdatedException(String messageKey, String value0, String value1) {
        return new NoUpdatedException(messageKey, this.localeUtils.getCurrentLocale(), value0, value1);
    }

    public InvalidCredentialsException invalidCredentialsException(String messageKey, String... params) {
        return new InvalidCredentialsException(messageKey, this.localeUtils.getCurrentLocale(), (Object) params);
    }
}
