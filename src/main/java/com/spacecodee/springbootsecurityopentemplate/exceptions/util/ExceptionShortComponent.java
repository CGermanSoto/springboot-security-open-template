package com.spacecodee.springbootsecurityopentemplate.exceptions.util;

import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.InvalidCredentialsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.InvalidPasswordException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.TokenExpiredException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.auth.TokenNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.base.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint.ModuleNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint.OperationNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.endpoint.PermissionNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.*;
import com.spacecodee.springbootsecurityopentemplate.exceptions.user.*;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.DoNotExistsByIdException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.PasswordDoNotMatchException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExceptionShortComponent {

    public TokenExpiredException tokenExpiredException(String messageKey, String locale) {
        return new TokenExpiredException(messageKey, locale);
    }

    public UserNotFoundException userNotFoundException(String messageKey, String locale) {
        return new UserNotFoundException(messageKey, locale);
    }

    public TokenNotFoundException tokenNotFoundException(String messageKey, String locale) {
        return new TokenNotFoundException(messageKey, locale);
    }

    public NoDeletedException noDeletedException(String messageKey, String locale) {
        return new NoDeletedException(messageKey, locale);
    }

    public AlreadyExistsException alreadyExistsException(String messageKey, String locale) {
        return new AlreadyExistsException(messageKey, locale);
    }

    public LastAdminException lastAdminException(String messageKey, String locale) {
        return new LastAdminException(messageKey, locale);
    }

    public LastDeveloperException lastDeveloperException(String messageKey, String locale) {
        return new LastDeveloperException(messageKey, locale);
    }

    public LastTechnicianException lastTechnicianException(String messageKey, String locale) {
        return new LastTechnicianException(messageKey, locale);
    }

    public DoNotExistsByIdException doNotExistsByIdException(String messageKey, String locale) {
        return new DoNotExistsByIdException(messageKey, locale);
    }

    public PasswordDoNotMatchException passwordDoNotMatchException(String messageKey, String locale) {
        return new PasswordDoNotMatchException(messageKey, locale);
    }

    public UsernameNotFoundException usernameNotFoundException(String messageKey, String locale) {
        return new UsernameNotFoundException(messageKey, locale);
    }

    public RoleNotFoundException roleNotFoundException(String messageKey, String locale) {
        return new RoleNotFoundException(messageKey, locale);
    }

    public CannotSaveException cannotSaveUpdateException(String messageKey, String locale) {
        return new CannotSaveException(messageKey, locale);
    }

    public CannotSaveException cannotSaveException(String messageKey, String locale) {
        return new CannotSaveException(messageKey, locale);
    }

    public ObjectNotFoundException notFoundException(String messageKey, String locale) {
        return new ObjectNotFoundException(messageKey, locale);
    }

    public InvalidPasswordException invalidPasswordException(String messageKey, String locale) {
        return new InvalidPasswordException(messageKey, locale);
    }

    public NoContentException noContentException(String messageKey, String locale) {
        return new NoContentException(messageKey, locale);
    }

    public NoCreatedException noCreatedException(String messageKey, String locale) {
        return new NoCreatedException(messageKey, locale);
    }

    public NoDisabledException noDisabledException(String messageKey, String locale) {
        return new NoDisabledException(messageKey, locale);
    }

    public @NotNull ModuleNotFoundException moduleNotFoundException(String messageKey, String locale) {
        return new ModuleNotFoundException(messageKey, locale);
    }

    public @NotNull OperationNotFoundException operationNotFoundException(String messageKey, String locale) {
        return new OperationNotFoundException(messageKey, locale);
    }

    public PermissionNotFoundException permissionNotFoundException(String messageKey, String locale) {
        return new PermissionNotFoundException(messageKey, locale);
    }

    public InvalidParameterException invalidParameterException(String messageKey, String locale) {
        return new InvalidParameterException(messageKey, locale);
    }

    public NoUpdatedException noUpdatedException(String messageKey, String locale) {
        return new NoUpdatedException(messageKey, locale);
    }

    public PasswordDoNotMatchException passwordsDoNotMatchException(String messageKey, String locale) {
        return new PasswordDoNotMatchException(messageKey, locale);
    }

    public InvalidCredentialsException invalidCredentialsException(String messageKey, String locale) {
        return new InvalidCredentialsException(messageKey, locale);
    }
}
