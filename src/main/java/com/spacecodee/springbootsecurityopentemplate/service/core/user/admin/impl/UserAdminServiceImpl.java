package com.spacecodee.springbootsecurityopentemplate.service.core.user.admin.impl;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminUVO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IUserMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.admin.IUserAdminService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenService;
import com.spacecodee.springbootsecurityopentemplate.service.validation.IUserValidationService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAdminServiceImpl implements IUserAdminService {
    private static final String ADMIN_PREFIX = "admin";

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
            throw this.exceptionShortComponent.noContentException("admin.added.failed", locale);
        }

        if (AppUtils.comparePasswords(adminAVO.password(), adminAVO.repeatPassword())) {
            throw this.exceptionShortComponent.passwordsDoNotMatchException("auth.password.do.not.match", locale);
        }

        this.userValidationService.validateUsername(adminAVO.username(), ADMIN_PREFIX, locale);
        var adminRoleEntity = this.roleService.findAdminRole(locale);
        var userEntity = this.userDTOMapper.toEntity(adminAVO);

        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoleEntity(adminRoleEntity);

        try {
            this.userRepository.save(userEntity);
        } catch (Exception e) {
            log.error("Error saving admin", e);
            log.info(ADMIN_PREFIX + ".added.failed");
            throw this.exceptionShortComponent.noCreatedException("admin.added.failed", locale);
        }
    }

    @Override
    @Transactional
    public void update(int id, @NotNull AdminUVO adminVO, String locale) {
        var existingAdmin = this.userValidationService.validateUserUpdate(id, adminVO.getUsername(), ADMIN_PREFIX, locale);
        boolean hasChanges = this.userValidationService.checkAndUpdateUserChanges(adminVO, existingAdmin);

        if (hasChanges) {
            saveAdminChanges(existingAdmin, locale);
        }
    }

    @Override
    @Transactional
    public void delete(int id, String locale) {
        var existingAdmin = this.userValidationService.validateUserUpdate(id, null, ADMIN_PREFIX, locale);
        validateLastAdmin(locale);

        try {
            this.jwtTokenService.deleteByUserId(locale, id);
            this.userRepository.delete(existingAdmin);
        } catch (Exception e) {
            log.error("Error deleting admin", e);
            throw this.exceptionShortComponent.noDeletedException(ADMIN_PREFIX + ".deleted.failed", locale);
        }
    }

    private void validateLastAdmin(String locale) {
        var adminCount = this.userRepository.countByRoleEntity_Name(RoleEnum.valueOf(this.adminRole));
        if (adminCount <= 1) {
            throw this.exceptionShortComponent.lastAdminException(ADMIN_PREFIX + ".deleted.failed.last", locale);
        }
    }

    private void saveAdminChanges(UserEntity admin, String locale) {
        try {
            this.jwtTokenService.deleteByUserId(locale, admin.getId());
            this.userRepository.save(admin);
        } catch (Exception e) {
            log.error("Error updating admin", e);
            throw this.exceptionShortComponent.noUpdatedException(ADMIN_PREFIX + ".updated.failed", locale);
        }
    }
}