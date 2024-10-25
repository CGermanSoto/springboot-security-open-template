package com.spacecodee.springbootsecurityopentemplate.service.impl.user;

import com.spacecodee.ticklyspace.data.vo.auth.AddAdminVO;
import com.spacecodee.ticklyspace.exceptions.CannotSaveException;
import com.spacecodee.ticklyspace.mappers.IUserMapper;
import com.spacecodee.ticklyspace.persistence.repository.IUserRepository;
import com.spacecodee.ticklyspace.service.IRoleService;
import com.spacecodee.ticklyspace.service.user.IUserAdminService;
import com.spacecodee.ticklyspace.utils.AppUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class UserAdminServiceImpl implements IUserAdminService {

    private final PasswordEncoder passwordEncoder;
    private static AppUtils appUtils;

    private final IUserRepository userRepository;
    private final IRoleService roleService;

    private final IUserMapper userDTOMapper;

    private static final Logger logger = Logger.getLogger(UserAdminServiceImpl.class.getName());

    @Override
    @Transactional
    public void add(AddAdminVO adminVO, String locale) {
        AppUtils.validatePassword(adminVO.getPassword(), adminVO.getRepeatPassword());
        this.alreadyExistByUsername(adminVO.getUsername());

        var adminRoleEntity = this.roleService.findAdminRole();
        var userEntity = this.userDTOMapper.toEntity(adminVO);

        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoleEntity(adminRoleEntity);

        try {
            this.userRepository.save(userEntity);
        } catch (CannotSaveException e) {
            logger.log(Level.SEVERE, "UserServiceImpl.addAdmin: error", e);
            throw new CannotSaveException("Cannot save user");
        }
    }

    private void alreadyExistByUsername(String username) {
        var alreadyExist = this.userRepository.existsByUsername(username);
        if (alreadyExist) {
            throw new CannotSaveException("User already exists with username: " + username);
        }
    }
}
