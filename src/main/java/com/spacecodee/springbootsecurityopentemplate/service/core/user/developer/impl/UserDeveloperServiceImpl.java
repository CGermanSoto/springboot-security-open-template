package com.spacecodee.springbootsecurityopentemplate.service.core.user.developer.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperUVO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IDeveloperMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.developer.IUserDeveloperService;
import com.spacecodee.springbootsecurityopentemplate.service.security.IJwtTokenService;
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
public class UserDeveloperServiceImpl implements IUserDeveloperService {
    private static final String DEVELOPER_PREFIX = "developer";

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
            log.error("Error saving developer", e);
            throw this.exceptionShortComponent.cannotSaveException("developer.added.failed", locale);
        }
    }

    @Override
    @Transactional
    public void update(int id, @NotNull DeveloperUVO developerUVO, String locale) {
        var existingDeveloper = this.userValidationService.validateUserUpdate(id, developerUVO.getUsername(), DEVELOPER_PREFIX, locale);
        boolean hasChanges = this.userValidationService.checkAndUpdateUserChanges(developerUVO, existingDeveloper);

        if (hasChanges) {
            saveDeveloperChanges(existingDeveloper, locale);
        }
    }

    @Override
    @Transactional
    public void delete(int id, String locale) {
        var existingDeveloper = this.userValidationService.validateUserUpdate(id, null, DEVELOPER_PREFIX, locale);
        validateLastDeveloper(locale);

        try {
            this.jwtTokenService.deleteByUserId(locale, id);
            this.userRepository.delete(existingDeveloper);
        } catch (Exception e) {
            log.error("Error deleting developer", e);
            throw this.exceptionShortComponent.noDeletedException("developer.deleted.failed", locale);
        }
    }

    @Override
    public DeveloperDTO findById(int id, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("developer.invalid.id", locale);
        }

        return this.userRepository.findById(id)
                .map(this.developerMapper::toDto)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(
                        "developer.not.exists.by.id",
                        locale));
    }

    @Override
    public List<DeveloperDTO> findAll(String locale) {
        var developers = this.userRepository.findByRoleEntity_Name(RoleEnum.valueOf(this.developerRole));
        return this.developerMapper.toDtoList(developers);
    }

    private void saveDeveloperChanges(UserEntity developer, String locale) {
        try {
            this.jwtTokenService.deleteByUserId(locale, developer.getId());
            this.userRepository.save(developer);
        } catch (Exception e) {
            log.error("Error updating developer", e);
            throw this.exceptionShortComponent.noUpdatedException("developer.updated.failed", locale);
        }
    }

    private void validateLastDeveloper(String locale) {
        var developersCount = this.userRepository.countByRoleEntity_Name(RoleEnum.valueOf(this.developerRole));
        if (developersCount <= 1) {
            throw this.exceptionShortComponent.lastDeveloperException("developer.deleted.failed.last", locale);
        }
    }
}
