package com.spacecodee.springbootsecurityopentemplate.service.impl.validation;

import org.springframework.stereotype.Service;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.validation.IUserValidationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserValidationServiceImpl implements IUserValidationService {
    private final IUserRepository userRepository;
    private final ExceptionShortComponent exceptionComponent;

    @Override
    public UserEntity validateUserUpdate(int id, String newUsername, String messagePrefix, String locale) {
        if (id <= 0) {
            throw this.exceptionComponent.invalidParameterException(messagePrefix + ".invalid.id", locale);
        }

        var existingUser = this.userRepository.findById(id)
                .orElseThrow(() -> this.exceptionComponent.doNotExistsByIdException(messagePrefix + ".not.exists.by.id",
                        locale));

        if (!existingUser.getUsername().equals(newUsername)) {
            validateUsername(newUsername, messagePrefix, locale);
        }

        return existingUser;
    }

    @Override
    public void validateLastUserByRole(String roleName, String messagePrefix, String locale) {
        var count = this.userRepository.countByRoleEntity_Name(RoleEnum.valueOf(roleName));
        if (count <= 1) {
            throw this.exceptionComponent.noDeletedException(messagePrefix + ".deleted.failed.last", locale);
        }
    }

    @Override
    public void validateUsername(String username, String messagePrefix, String locale) {
        if (this.userRepository.existsByUsername(username)) {
            throw this.exceptionComponent.alreadyExistsException(messagePrefix + ".exists.by.username", locale);
        }
    }
}
