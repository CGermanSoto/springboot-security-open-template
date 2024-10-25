package com.spacecodee.springbootsecurityopentemplate.service.impl.user;

import com.spacecodee.ticklyspace.data.vo.auth.AddAdminVO;
import com.spacecodee.ticklyspace.exceptions.CannotSaveException;
import com.spacecodee.ticklyspace.mappers.IUserMapper;
import com.spacecodee.ticklyspace.persistence.repository.IUserRepository;
import com.spacecodee.ticklyspace.service.IRoleService;
import com.spacecodee.ticklyspace.service.user.IUserService;
import com.spacecodee.ticklyspace.utils.AppUtils;
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

    // Repositories
    private final IUserRepository userRepository;
    private final IRoleService roleService;

    // Mappers
    private final IUserMapper userDTOMapper;

    // add logger
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public void addUser(AddAdminVO adminVO, String lang) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void alreadyExistByUsername(String username) {
        var alreadyExist = this.userRepository.existsByUsername(username);
        if (alreadyExist) {
            throw new CannotSaveException("User already exists with username: " + username);
        }
    }
}
