package com.spacecodee.springbootsecurityopentemplate.service.validation.impl;

import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.validation.IUserValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserValidationServiceImpl implements IUserValidationService {
    private final IUserRepository userRepository;
    private final ExceptionShortComponent exceptionComponent;

    @Override
    public void validateUsername(String username, String messagePrefix, String locale) {
        if (this.userRepository.existsByUsername(username)) {
            log.info("{}.exists.by.username", messagePrefix);
            throw this.exceptionComponent.alreadyExistsException(messagePrefix + ".exists.by.username", locale);
        }
    }
}
