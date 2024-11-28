package com.spacecodee.springbootsecurityopentemplate.service.impl.user;

import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminUVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.CannotSaveException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.mappers.basic.IUserMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.IUserRepository;
import com.spacecodee.springbootsecurityopentemplate.service.IJwtTokenService;
import com.spacecodee.springbootsecurityopentemplate.service.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.user.IUserAdminService;
import com.spacecodee.springbootsecurityopentemplate.utils.AppUtils;
import com.spacecodee.springbootsecurityopentemplate.utils.UserUpdateUtils;
import jakarta.transaction.Transactional;
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
    private final IJwtTokenService jwtTokenService;

    private final IUserMapper userDTOMapper;

    @Value("${security.default.roles}")
    private String adminRole;

    private final Logger logger = Logger.getLogger(UserAdminServiceImpl.class.getName());

    public UserAdminServiceImpl(PasswordEncoder passwordEncoder, ExceptionShortComponent exceptionShortComponent,
                                IUserRepository userRepository, IRoleService roleService, IJwtTokenService jwtTokenService,
                                IUserMapper userDTOMapper) {
        this.passwordEncoder = passwordEncoder;
        this.exceptionShortComponent = exceptionShortComponent;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.jwtTokenService = jwtTokenService;
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
                this.jwtTokenService.deleteByUserId(locale, existingAdmin.getId());
                this.userRepository.save(existingAdmin);
            } catch (Exception e) {
                this.logger.log(Level.SEVERE, "Error updating admin", e);
                throw this.exceptionShortComponent.noUpdatedException("admin.updated.failed", locale);
            }
        }
    }

    @Override
    @Transactional
    public void delete(int id, String locale) {
        if (id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("admin.invalid.id", locale);
        }

        var admin = this.userRepository.findById(id)
                .orElseThrow(() -> this.exceptionShortComponent.doNotExistsByIdException("admin.not.exists.by.id", locale));

        // Check if it's the last admin
        var adminCount = this.userRepository.countByRoleEntity_Name(AppUtils.getRoleEnum(this.adminRole));
        if (adminCount <= 1) {
            this.logger.log(Level.WARNING, "Attempted to delete last admin with ID: {0}", id);
            throw this.exceptionShortComponent.lastAdminException("admin.deleted.failed.last", locale);
        }

        try {
            // Delete associated tokens first
            this.jwtTokenService.deleteByUserId(locale, id);
            this.userRepository.delete(admin);
            this.logger.log(Level.INFO, "Successfully deleted admin with ID: {0}", id);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error deleting admin with ID: {0}", id);
            throw this.exceptionShortComponent.noDeletedException("admin.deleted.failed", locale);
        }
    }

    private void alreadyExistByUsername(String username, String locale) {
        var alreadyExists = this.userRepository.existsByUsername(username);
        if (alreadyExists) {
            throw this.exceptionShortComponent.alreadyExistsException("admin.exists.by.username", locale);
        }
    }
}