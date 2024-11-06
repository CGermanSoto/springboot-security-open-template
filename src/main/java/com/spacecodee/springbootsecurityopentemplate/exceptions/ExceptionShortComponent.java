package com.spacecodee.springbootsecurityopentemplate.exceptions;

import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ExceptionShortComponent {

    private final MessageUtilComponent messageUtilComponent;

    public TokenNotFoundException tokenNotFoundException(String message, String locale) {
        return new TokenNotFoundException(this.messageUtilComponent.getMessage(message, locale));
    }

    public NoDeletedException noDeletedException(String message, String locale) {
        return new NoDeletedException(this.messageUtilComponent.getMessage(message, locale));
    }

    public AlreadyExistsException alreadyExistsException(String message, String locale) {
        return new AlreadyExistsException(this.messageUtilComponent.getMessage(message, locale));
    }

    public LastAdminException lastAdminException(String message, String locale) {
        return new LastAdminException(this.messageUtilComponent.getMessage(message, locale));
    }

    public DoNotExistsByIdException doNotExistsByIdException(String message, String locale) {
        return new DoNotExistsByIdException(this.messageUtilComponent.getMessage(message, locale));
    }

    public PasswordDoNotMatchException passwordDoNotMatchException(String message, String locale) {
        return new PasswordDoNotMatchException(this.messageUtilComponent.getMessage(message, locale));
    }

    public UsernameNotFoundException usernameNotFoundException(String message, String locale) {
        return new UsernameNotFoundException(this.messageUtilComponent.getMessage(message, locale));
    }

    public RoleNotFoundException roleNotFoundException(String message, String locale) {
        return new RoleNotFoundException(this.messageUtilComponent.getMessage(message, locale));
    }

    public CannotSaveException cannotSaveUpdateException(String message, String locale) {
        return new CannotSaveException(this.messageUtilComponent.getMessage(message, locale));
    }

    public ObjectNotFoundException notFoundException(String message, String locale) {
        return new ObjectNotFoundException(this.messageUtilComponent.getMessage(message, locale));
    }

    public InvalidPasswordException invalidPasswordException(String message, String locale) {
        return new InvalidPasswordException(this.messageUtilComponent.getMessage(message, locale));
    }

    public NoContentException noContentException(String message, String locale) {
        return new NoContentException(this.messageUtilComponent.getMessage(message, locale));
    }

    public NoCreatedException noCreatedException(String message, String locale) {
        return new NoCreatedException(this.messageUtilComponent.getMessage(message, locale));
    }

    public NoDisabledException noDisabledException(String message, String locale) {
        return new NoDisabledException(this.messageUtilComponent.getMessage(message, locale));
    }
}
