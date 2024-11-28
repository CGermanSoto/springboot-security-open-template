package com.spacecodee.springbootsecurityopentemplate.service.impl.user;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperUVO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IDeveloperMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.user.IUserDeveloperService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import com.spacecodee.springbootsecurityopentemplate.utils.UserUpdateUtils;
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

    private final Logger logger = Logger.getLogger(UserDeveloperServiceImpl.class.getName());
    private final PasswordEncoder passwordEncoder;
    private final ExceptionShortComponent exceptionShortComponent;
    private final IUserRepository userRepository;
    private final IRoleService roleService;
    private final IDeveloperMapper developerMapper;

    @Value("${security.default.developer.role}")
    private String developerRole;

    private static final String DEVELOPER_INVALID_ID = "developer.invalid.id";
    private static final String DEVELOPER_NOT_EXISTS_BY_ID = "developer.not.exists.by.id";

    public UserDeveloperServiceImpl(PasswordEncoder passwordEncoder, ExceptionShortComponent exceptionShortComponent,
                                    IUserRepository userRepository, IRoleService roleService, IDeveloperMapper developerMapper) {
        this.passwordEncoder = passwordEncoder;
        this.exceptionShortComponent = exceptionShortComponent;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.developerMapper = developerMapper;
    }

    @Override
    @Transactional
    public void add(@NotNull DeveloperAVO developerAVO, String locale) {
        if (AppUtils.comparePasswords(developerAVO.getPassword(), developerAVO.getRepeatPassword())) {
            throw this.exceptionShortComponent.passwordsDoNotMatchException("auth.password.do.not.match", locale);
        }

        this.alreadyExistByUsername(developerAVO.getUsername(), locale);

        var roleEntity = this.roleService.findByName(this.developerRole, locale);
        var developerEntity = this.developerMapper.voToEntity(developerAVO);

        developerEntity.setPassword(this.passwordEncoder.encode(developerAVO.getPassword()));
        developerEntity.setRoleEntity(roleEntity);

        try {
            this.userRepository.save(developerEntity);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error saving developer", e);
            throw this.exceptionShortComponent.cannotSaveException("developer.added.failed", locale);
        }
    }

    @Override
    @Transactional
    public void update(int id, DeveloperUVO developerUVO, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(DEVELOPER_INVALID_ID, locale);
        }

        var existingDeveloper = this.userRepository.findById(id)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(DEVELOPER_NOT_EXISTS_BY_ID,
                        locale));

        if (!existingDeveloper.getUsername().equals(developerUVO.getUsername())) {
            this.alreadyExistByUsername(developerUVO.getUsername(), locale);
        }

        boolean hasChanges = UserUpdateUtils.checkForChanges(developerUVO, existingDeveloper);

        if (hasChanges) {
            try {
                this.userRepository.save(existingDeveloper);
            } catch (Exception e) {
                this.logger.log(Level.SEVERE, "Error updating developer", e);
                throw this.exceptionShortComponent.noUpdatedException("developer.updated.failed", locale);
            }
        }
    }

    @Override
    @Transactional
    public void delete(int id, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(DEVELOPER_INVALID_ID, locale);
        }

        var developer = this.userRepository.findById(id)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(DEVELOPER_NOT_EXISTS_BY_ID,
                        locale));

        try {
            this.userRepository.delete(developer);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error deleting developer", e);
            throw this.exceptionShortComponent.noDeletedException("developer.deleted.failed", locale);
        }
    }

    @Override
    public DeveloperDTO findById(int id, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(DEVELOPER_INVALID_ID, locale);
        }

        return this.userRepository.findById(id)
                .map(this.developerMapper::toDto)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException(DEVELOPER_NOT_EXISTS_BY_ID,
                        locale));
    }

    @Override
    public List<DeveloperDTO> findAll(String locale) {
        var developers = this.userRepository.findByRoleEntity_Name(RoleEnum.valueOf(this.developerRole));
        return this.developerMapper.toDtoList(developers);
    }

    private void alreadyExistByUsername(String username, String locale) {
        if (this.userRepository.existsByUsername(username)) {
            throw this.exceptionShortComponent.alreadyExistsException("developer.exists.by.username", locale);
        }
    }
}
