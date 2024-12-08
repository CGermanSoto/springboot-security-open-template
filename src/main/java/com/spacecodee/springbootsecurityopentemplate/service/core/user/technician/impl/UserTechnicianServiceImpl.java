package com.spacecodee.springbootsecurityopentemplate.service.core.user.technician.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.TechnicianDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianUVO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.ITechnicianMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.technician.IUserTechnicianService;
import com.spacecodee.springbootsecurityopentemplate.service.security.ITokenServiceFacade;
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
public class UserTechnicianServiceImpl implements IUserTechnicianService {
    private static final String TECHNICIAN_INVALID_ID = "technician.invalid.id";
    private static final String TECHNICIAN_NOT_EXISTS_BY_ID = "technician.not.exists.by.id";
    private static final String TECHNICIAN_PREFIX = "technician";

    private final PasswordEncoder passwordEncoder;
    private final ExceptionShortComponent exceptionShortComponent;
    private final IUserRepository userRepository;
    private final IRoleService roleService;
    private final ITokenServiceFacade tokenServiceFacade;
    private final ITechnicianMapper technicianMapper;
    private final IUserValidationService userValidationService;

    @Value("${security.default.technician.role}")
    private String technicianRole;

    public UserTechnicianServiceImpl(PasswordEncoder passwordEncoder,
            ExceptionShortComponent exceptionShortComponent,
            IUserRepository userRepository,
            IRoleService roleService,
            ITokenServiceFacade tokenServiceFacade,
            ITechnicianMapper technicianMapper,
            IUserValidationService userValidationService) {
        this.passwordEncoder = passwordEncoder;
        this.exceptionShortComponent = exceptionShortComponent;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.tokenServiceFacade = tokenServiceFacade;
        this.technicianMapper = technicianMapper;
        this.userValidationService = userValidationService;
    }

    @Override
    @Transactional
    public void add(@NotNull TechnicianAVO technicianAVO, String locale) {
        if (!AppUtils.comparePasswords(technicianAVO.getPassword(), technicianAVO.getRepeatPassword())) {
            throw this.exceptionShortComponent.passwordsDoNotMatchException("auth.password.do.not.match", locale);
        }

        this.userValidationService.validateUsername(technicianAVO.getUsername(), TECHNICIAN_PREFIX, locale);
        var roleEntity = this.roleService.findByName(this.technicianRole, locale);

        var technicianEntity = this.technicianMapper.voToEntity(technicianAVO);
        technicianEntity.setPassword(this.passwordEncoder.encode(technicianAVO.getPassword()));
        technicianEntity.setRoleEntity(roleEntity);

        try {
            this.userRepository.save(technicianEntity);
        } catch (Exception e) {
            log.error("Error saving technician", e);
            throw this.exceptionShortComponent.cannotSaveException("technician.added.failed", locale);
        }
    }

    @Override
    @Transactional
    public void update(int id, @NotNull TechnicianUVO technicianUVO, String locale) {
        var existingTechnician = this.userValidationService.validateUserUpdate(id, technicianUVO.getUsername(),
                TECHNICIAN_PREFIX, locale);
        boolean hasChanges = this.userValidationService.checkAndUpdateUserChanges(technicianUVO, existingTechnician);

        if (hasChanges) {
            this.saveTechnicianChanges(existingTechnician, locale);
        }
    }

    @Override
    @Transactional
    public void delete(int id, String locale) {
        var existingTechnician = this.userValidationService.validateUserUpdate(id, null, TECHNICIAN_PREFIX, locale);
        validateLastTechnician(locale);

        try {
            this.tokenServiceFacade.logout(String.valueOf(id), locale);
            this.userRepository.delete(existingTechnician);
        } catch (Exception e) {
            log.error("Error deleting technician", e);
            throw this.exceptionShortComponent.noDeletedException("technician.deleted.failed", locale);
        }
    }

    @Override
    public TechnicianDTO findById(int id, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(TECHNICIAN_INVALID_ID, locale);
        }

        return this.userRepository.findById(id)
                .map(this.technicianMapper::toDto)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(
                        TECHNICIAN_NOT_EXISTS_BY_ID, locale));
    }

    @Override
    public List<TechnicianDTO> findAll(String locale) {
        var technicians = this.userRepository.findByRoleEntity_Name(RoleEnum.valueOf(this.technicianRole));
        return this.technicianMapper.toDtoList(technicians);
    }

    private void saveTechnicianChanges(UserEntity technician, String locale) {
        try {
            this.tokenServiceFacade.logout(String.valueOf(technician.getId()), locale);
            this.userRepository.save(technician);
        } catch (Exception e) {
            log.error("Error updating technician", e);
            throw this.exceptionShortComponent.noUpdatedException("technician.updated.failed", locale);
        }
    }

    private void validateLastTechnician(String locale) {
        var techniciansCount = this.userRepository.countByRoleEntity_Name(RoleEnum.valueOf(this.technicianRole));
        if (techniciansCount <= 1) {
            throw this.exceptionShortComponent.lastTechnicianException("technician.deleted.failed.last", locale);
        }
    }
}