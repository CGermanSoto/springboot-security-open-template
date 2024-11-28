package com.spacecodee.springbootsecurityopentemplate.service.core.user.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperUVO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IDeveloperMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenService;
import com.spacecodee.springbootsecurityopentemplate.service.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.IUserDeveloperService;
import com.spacecodee.springbootsecurityopentemplate.service.validation.IUserValidationService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserDeveloperServiceImpl implements IUserDeveloperService {
    private static final String DEVELOPER_PREFIX = "developer";

    private final Logger logger = Logger.getLogger(UserDeveloperServiceImpl.class.getName());
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IRoleService roleService;
    private final IJwtTokenService jwtTokenService;
    private final IDeveloperMapper developerMapper;
    private final IUserValidationService userValidationService;
    private final ExceptionShortComponent exceptionShortComponent;

    @Value("${security.default.developer.role}")
    private String developerRole;

    public UserDeveloperServiceImpl(PasswordEncoder passwordEncoder, IUserRepository userRepository,
                                    IRoleService roleService, IJwtTokenService jwtTokenService, IDeveloperMapper developerMapper,
                                    IUserValidationService userValidationService, ExceptionShortComponent exceptionShortComponent) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.jwtTokenService = jwtTokenService;
        this.developerMapper = developerMapper;
        this.userValidationService = userValidationService;
        this.exceptionShortComponent = exceptionShortComponent;
    }

    @Override
    @Transactional
    public void add(@NotNull DeveloperAVO developerAVO, String locale) {
        if (AppUtils.comparePasswords(developerAVO.getPassword(), developerAVO.getRepeatPassword())) {
            throw this.exceptionShortComponent.passwordsDoNotMatchException("auth.password.do.not.match", locale);
        }

        this.userValidationService.validateUsername(developerAVO.getUsername(), DEVELOPER_PREFIX, locale);
        var roleEntity = this.roleService.findByName(this.developerRole, locale);
        var developerEntity = this.developerMapper.voToEntity(developerAVO);

        developerEntity.setPassword(this.passwordEncoder.encode(developerAVO.getPassword()));
        developerEntity.setRoleEntity(roleEntity);

        try {
            this.userRepository.save(developerEntity);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error saving developer", e);
            throw this.exceptionShortComponent.cannotSaveException(DEVELOPER_PREFIX + ".added.failed", locale);
        }
    }

    @Override
    @Transactional
    public void update(int id, @NotNull DeveloperUVO developerUVO, String locale) {
        var existingDeveloper = this.userValidationService.validateUserUpdate(id, developerUVO.getUsername(),
                DEVELOPER_PREFIX, locale);
        boolean hasChanges = this.userValidationService.checkAndUpdateUserChanges(developerUVO, existingDeveloper);

        if (hasChanges) {
            try {
                this.jwtTokenService.deleteByUserId(locale, existingDeveloper.getId());
                this.userRepository.save(existingDeveloper);
            } catch (Exception e) {
                this.logger.log(Level.SEVERE, "Error updating developer", e);
                throw this.exceptionShortComponent.noUpdatedException(DEVELOPER_PREFIX + ".updated.failed", locale);
            }
        }
    }

    @Override
    @Transactional
    public void delete(int id, String locale) {
        var existingDeveloper = this.userValidationService.validateUserUpdate(id, null, DEVELOPER_PREFIX, locale);
        this.userValidationService.validateLastUserByRole(this.developerRole, DEVELOPER_PREFIX, locale);

        try {
            this.jwtTokenService.deleteByUserId(locale, id);
            this.userRepository.delete(existingDeveloper);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error deleting developer", e);
            throw this.exceptionShortComponent.noDeletedException(DEVELOPER_PREFIX + ".deleted.failed", locale);
        }
    }

    @Override
    public DeveloperDTO findById(int id, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(DEVELOPER_PREFIX + ".invalid.id", locale);
        }

        return this.userRepository.findById(id)
                .map(this.developerMapper::toDto)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(
                        DEVELOPER_PREFIX + ".not.exists.by.id",
                        locale));
    }

    @Override
    public List<DeveloperDTO> findAll(String locale) {
        var developers = this.userRepository.findByRoleEntity_Name(RoleEnum.valueOf(this.developerRole));
        return this.developerMapper.toDtoList(developers);
    }
}
