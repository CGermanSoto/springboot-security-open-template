package com.spacecodee.springbootsecurityopentemplate.service.validation;

public interface IUserValidationService {

    void validateUsername(String username, String messagePrefix, String locale);
}