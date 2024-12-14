package com.spacecodee.springbootsecurityopentemplate.service.core.user.admin.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.AdminDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminUVO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IAdminMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.admin.IUserAdminService;
import com.spacecodee.springbootsecurityopentemplate.service.security.ITokenServiceFacade;
import com.spacecodee.springbootsecurityopentemplate.service.security.password.IPasswordValidationService;
import com.spacecodee.springbootsecurityopentemplate.service.validation.IUserValidationService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserAdminServiceImpl implements IUserAdminService {
    private static final String ADMIN_PREFIX = "admin";

    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IRoleService roleService;
    private final ITokenServiceFacade tokenServiceFacade;
    private final IAdminMapper userDTOMapper;
    private final IUserValidationService userValidationService;
    private final ExceptionShortComponent exceptionShortComponent;
    private final IPasswordValidationService passwordValidationService;

    @Value("${security.default.roles}")
    private String adminRole;

    public UserAdminServiceImpl(PasswordEncoder passwordEncoder,
            IUserRepository userRepository,
            IRoleService roleService,
            ITokenServiceFacade tokenServiceFacade,
            IAdminMapper userDTOMapper,
            IUserValidationService userValidationService,
            ExceptionShortComponent exceptionShortComponent,
            IPasswordValidationService passwordValidationService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.tokenServiceFacade = tokenServiceFacade;
        this.userDTOMapper = userDTOMapper;
        this.userValidationService = userValidationService;
        this.exceptionShortComponent = exceptionShortComponent;
        this.passwordValidationService = passwordValidationService;
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

        // Add password validation
        this.passwordValidationService.validatePassword(adminAVO.getPassword(), locale);

        this.userValidationService.validateUsername(adminAVO.getUsername(), ADMIN_PREFIX, locale);
        var adminRoleEntity = this.roleService.findAdminRole(locale);
        var userEntity = this.userDTOMapper.toEntity(adminAVO);

        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoleEntity(adminRoleEntity);

        try {
            this.userRepository.save(userEntity);
        } catch (Exception e) {
            log.error("Error saving admin", e);
            throw this.exceptionShortComponent.noCreatedException("admin.added.failed", locale);
        }
    }

    @Override
    @Transactional
    public void update(int id, @NotNull AdminUVO adminVO, String locale) {
        var existingAdmin = this.userValidationService.validateUserUpdate(id, adminVO.getUsername(), ADMIN_PREFIX,
                locale);
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
            this.tokenServiceFacade.logoutByUserId(id, locale);
            this.userRepository.delete(existingAdmin);
        } catch (Exception e) {
            log.error("Error deleting admin", e);
            throw this.exceptionShortComponent.noDeletedException("admin.deleted.failed", locale);
        }
    }

    @Override
    public AdminDTO findById(int id, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("admin.invalid.id", locale);
        }

        return this.userRepository.findById(id)
                .map(this.userDTOMapper::toDto)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(
                        "admin.not.exists.by.id",
                        locale));
    }

    @Override
    public List<AdminDTO> findAll(String locale) {
        var admins = this.userRepository.findByRoleEntity_Name(RoleEnum.valueOf(this.adminRole));
        return this.userDTOMapper.toDtoList(admins);
    }

    private void validateLastAdmin(String locale) {
        var adminCount = this.userRepository.countByRoleEntity_Name(RoleEnum.valueOf(this.adminRole));
        if (adminCount <= 1) {
            throw this.exceptionShortComponent.lastAdminException("admin.deleted.failed.last", locale);
        }
    }

    private void saveAdminChanges(UserEntity admin, String locale) {
        try {
            this.tokenServiceFacade.logoutByUserId(admin.getId(), locale);
            this.userRepository.save(admin);
        } catch (Exception e) {
            log.error("Error updating admin", e);
            throw this.exceptionShortComponent.noUpdatedException("admin.updated.failed", locale);
        }
    }
}