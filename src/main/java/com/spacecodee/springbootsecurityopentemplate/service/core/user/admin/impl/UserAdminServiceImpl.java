package com.spacecodee.springbootsecurityopentemplate.service.core.user.admin.impl;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminUVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IUserMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenService;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.admin.IUserAdminService;
import com.spacecodee.springbootsecurityopentemplate.service.validation.IUserValidationService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserAdminServiceImpl implements IUserAdminService {
    private static final String ADMIN_PREFIX = "admin";

    private final Logger logger = Logger.getLogger(UserAdminServiceImpl.class.getName());
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IRoleService roleService;
    private final IJwtTokenService jwtTokenService;
    private final IUserMapper userDTOMapper;
    private final IUserValidationService userValidationService;
    private final ExceptionShortComponent exceptionShortComponent;

    @Value("${security.default.roles}")
    private String adminRole;

    public UserAdminServiceImpl(PasswordEncoder passwordEncoder, IUserRepository userRepository,
                                IRoleService roleService, IJwtTokenService jwtTokenService, IUserMapper userDTOMapper,
                                IUserValidationService userValidationService, ExceptionShortComponent exceptionShortComponent) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.jwtTokenService = jwtTokenService;
        this.userDTOMapper = userDTOMapper;
        this.userValidationService = userValidationService;
        this.exceptionShortComponent = exceptionShortComponent;
    }

    @Override
    @Transactional
    public void add(AdminAVO adminAVO, String locale) {
        if (adminAVO == null) {
            throw this.exceptionShortComponent.noContentException(ADMIN_PREFIX + ".added.failed", locale);
        }

        if (AppUtils.comparePasswords(adminAVO.getPassword(), adminAVO.getRepeatPassword())) {
            throw this.exceptionShortComponent.passwordsDoNotMatchException("auth.password.do.not.match", locale);
        }

        this.userValidationService.validateUsername(adminAVO.getUsername(), ADMIN_PREFIX, locale);
        var adminRoleEntity = this.roleService.findAdminRole(locale);
        var userEntity = this.userDTOMapper.toEntity(adminAVO);

        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoleEntity(adminRoleEntity);

        try {
            this.userRepository.save(userEntity);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error saving admin", e);
            throw this.exceptionShortComponent.noCreatedException(ADMIN_PREFIX + ".added.failed", locale);
        }
    }

    @Override
    @Transactional
    public void update(int id, @NotNull AdminUVO adminVO, String locale) {
        var existingAdmin = this.userValidationService.validateUserUpdate(id, adminVO.getUsername(), ADMIN_PREFIX,
                locale);
        boolean hasChanges = this.userValidationService.checkAndUpdateUserChanges(adminVO, existingAdmin);

        if (hasChanges) {
            try {
                this.jwtTokenService.deleteByUserId(locale, existingAdmin.getId());
                this.userRepository.save(existingAdmin);
            } catch (Exception e) {
                this.logger.log(Level.SEVERE, "Error updating admin", e);
                throw this.exceptionShortComponent.noUpdatedException(ADMIN_PREFIX + ".updated.failed", locale);
            }
        }
    }

    @Override
    @Transactional
    public void delete(int id, String locale) {
        var existingAdmin = this.userValidationService.validateUserUpdate(id, null, ADMIN_PREFIX, locale);
        this.userValidationService.validateLastUserByRole(this.adminRole, ADMIN_PREFIX, locale);

        try {
            this.jwtTokenService.deleteByUserId(locale, id);
            this.userRepository.delete(existingAdmin);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error deleting admin", e);
            throw this.exceptionShortComponent.noDeletedException(ADMIN_PREFIX + ".deleted.failed", locale);
        }
    }
}