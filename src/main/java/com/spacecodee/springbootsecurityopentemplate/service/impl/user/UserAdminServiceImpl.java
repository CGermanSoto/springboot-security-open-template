package com.spacecodee.springbootsecurityopentemplate.service.impl.user;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminUVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.CannotSaveException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.NoDeletedException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.NoUpdatedException;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IUserMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.user.IUserAdminService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import com.spacecodee.springbootsecurityopentemplate.utils.UserUpdateUtils;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserAdminServiceImpl implements IUserAdminService {

    private final PasswordEncoder passwordEncoder;
    private final ExceptionShortComponent exceptionShortComponent;

    private final IUserRepository userRepository;
    private final IRoleService roleService;

    private final IUserMapper userDTOMapper;

    @Value("${security.default.roles}")
    private String adminRole;

    private final Logger logger = Logger.getLogger(UserAdminServiceImpl.class.getName());

    public UserAdminServiceImpl(PasswordEncoder passwordEncoder, ExceptionShortComponent exceptionShortComponent,
            IUserRepository userRepository, IRoleService roleService, IUserMapper userDTOMapper) {
        this.passwordEncoder = passwordEncoder;
        this.exceptionShortComponent = exceptionShortComponent;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    @Transactional
    public void add(AdminAVO adminAVO, String locale) {
        if (adminAVO == null) {
            throw this.exceptionShortComponent.noContentException("admin.added.failed", locale);
        }

        if (AppUtils.comparePasswords(adminAVO.getPassword(), adminAVO.getRepeatPassword())) {
            throw this.exceptionShortComponent.passwordsDoNotMatchException("auth.password.do.not.match", locale);
        }
        this.alreadyExistByUsername(adminAVO.getUsername(), locale);

        var adminRoleEntity = this.roleService.findAdminRole(locale);
        var userEntity = this.userDTOMapper.toEntity(adminAVO);

        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoleEntity(adminRoleEntity);

        try {
            this.userRepository.save(userEntity);
        } catch (CannotSaveException e) {
            this.logger.log(Level.SEVERE, "UserServiceImpl.addAdmin: error", e);
            throw this.exceptionShortComponent.noCreatedException("admin.added.failed", locale);
        }
    }

    @Override
    @Transactional
    public void update(int id, AdminUVO adminVO, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("admin.invalid.id", locale);
        }

        var existingAdmin = this.userRepository.findById(id)
                .orElseThrow(
                        () -> this.exceptionShortComponent.doNotExistsByIdException("admin.not.exists.by.id", locale));

        if (!existingAdmin.getUsername().equals(adminVO.getUsername())) {
            this.alreadyExistByUsername(adminVO.getUsername(), locale);
        }

        boolean hasChanges = UserUpdateUtils.checkForChanges(adminVO, existingAdmin);

        if (hasChanges) {
            try {
                this.userRepository.save(existingAdmin);
            } catch (Exception e) {
                this.logger.log(Level.SEVERE, "Error updating admin", e);
                throw this.exceptionShortComponent.noUpdatedException("admin.updated.failed", locale);
            }
        }
    }

    @Override
    public void delete(int id, String locale) {
        this.doNotExistsById(id, locale);
        this.howManyAdmins(locale);

        try {
            this.userRepository.deleteById(id);
        } catch (NoDeletedException e) {
            this.logger.log(Level.SEVERE, "UserServiceImpl.delete: error", e);
            throw this.exceptionShortComponent.noDeletedException("admin.deleted.failed", locale);
        }
    }

    private void alreadyExistByUsername(String username, String locale) {
        var alreadyExists = this.userRepository.existsByUsername(username);
        if (alreadyExists) {
            throw this.exceptionShortComponent.alreadyExistsException("admin.exists.by.username", locale);
        }
    }

    private void doNotExistsById(int id, String locale) {
        var doNotExistsById = this.userRepository.existsById(id);
        if (!doNotExistsById) {
            this.logger.log(Level.SEVERE, "UserServiceImpl.doNotExistsById: {0}", false);
            throw this.exceptionShortComponent.doNotExistsByIdException("admin.not.exists.by.id", locale);
        }
    }

    /**
     * Checks the number of admin users in the system and throws an exception if
     * there is only one admin left.
     *
     * @param locale the locale to use for exception messages
     */
    private void howManyAdmins(String locale) {
        // Convert the admin role from String to the appropriate enum type
        var adminRoleEnum = AppUtils.getRoleEnum(adminRole);

        // Count the number of admin users with the specified role
        var howManyAdmins = this.userRepository.countAdmins(adminRoleEnum);

        // If there is only one admin left, log the count and throw an exception
        if (howManyAdmins == 1) {
            this.logger.log(Level.SEVERE, "UserServiceImpl.howManyAdmins: {0}", howManyAdmins);
            throw this.exceptionShortComponent.lastAdminException("admin.deleted.failed.last", locale);
        }
    }
}
