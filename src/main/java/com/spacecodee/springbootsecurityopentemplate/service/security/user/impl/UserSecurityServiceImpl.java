package com.spacecodee.springbootsecurityopentemplate.service.security.user.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.security.IUserSecurityMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.security.user.IUserSecurityRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.user.IUserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements IUserSecurityService {

    private final IUserSecurityRepository userRepository;
    private final IUserSecurityMapper userDetailsMapper;
    private final ExceptionShortComponent exceptionShortComponent;

    private static final String USER_NOT_EXISTS_BY_USERNAME = "user.not.exists.by.username";

    @Transactional(readOnly = true, noRollbackFor = UsernameNotFoundException.class)
    @Override
    public UserSecurityDTO findByUsername(@NotNull String username) {
        return this.userRepository.findByUsername(username)
                .map(this.userDetailsMapper::toUserSecurityDTO)
                .orElseThrow(
                        () -> this.exceptionShortComponent.objectNotFoundException(UserSecurityServiceImpl.USER_NOT_EXISTS_BY_USERNAME));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(this.userDetailsMapper::toUserSecurityDTO)
                .orElseThrow(
                        () -> this.exceptionShortComponent.usernameNotFoundException(UserSecurityServiceImpl.USER_NOT_EXISTS_BY_USERNAME));
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findUserEntityByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> this.exceptionShortComponent.userNotFoundException(UserSecurityServiceImpl.USER_NOT_EXISTS_BY_USERNAME,
                        username));
    }
}
