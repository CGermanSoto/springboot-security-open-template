package com.spacecodee.springbootsecurityopentemplate.service.user.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.UserProfileDTO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.user.IUserMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.user.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    public UserEntity getUserEntityById(int id) {
        return this.userRepository.findById(id)
                .orElseThrow(
                        () -> this.exceptionShortComponent.objectNotFoundException("user.not.found", String.valueOf(id)));
    }

    @Override
    public UserProfileDTO getUserProfile(int id) {
        UserEntity userEntity = this.getUserEntityById(id);
        return this.userMapper.toProfileDTO(userEntity);
    }
}
