package com.spacecodee.springbootsecurityopentemplate.service.impl.user;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.CannotSaveException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IUserMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.user.IUserService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    // Password encoder
    private final PasswordEncoder passwordEncoder;
    private static AppUtils appUtils;
    private final ExceptionShortComponent exceptionShortComponent;

    // Repositories
    private final IUserRepository userRepository;
    private final IRoleService roleService;

    // Mappers
    private final IUserMapper userDTOMapper;

    // add logger
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public void addUser(AdminAVO adminAVO, String lang) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void alreadyExistByUsername(String username) {
        var alreadyExist = this.userRepository.existsByUsername(username);
        if (alreadyExist) {
            throw new CannotSaveException("User already exists with username: " + username);
        }
    }
}
