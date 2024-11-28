// IUserValidationService.java
package com.spacecodee.springbootsecurityopentemplate.validations.service;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;

public interface IUserValidationService {

    boolean checkAndUpdateUserChanges(Object userVO, UserEntity existingUser);

    UserEntity validateUserUpdate(int id, String newUsername, String messagePrefix, String locale);

    void validateLastUserByRole(String roleName, String messagePrefix, String locale);

    void validateUsername(String username, String messagePrefix, String locale);
}