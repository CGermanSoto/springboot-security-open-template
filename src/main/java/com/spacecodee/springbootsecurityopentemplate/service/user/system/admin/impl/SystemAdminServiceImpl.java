package com.spacecodee.springbootsecurityopentemplate.service.user.system.admin.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.system.admin.SystemAdminDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.system.admin.SystemAdminDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.CreateSystemAdminVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.SystemAdminFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin.UpdateSystemAdminVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.mappers.user.system.admin.ISystemAdminMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.user.ISystemAdminRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtTokenSecurityService;
import com.spacecodee.springbootsecurityopentemplate.service.user.system.admin.ISystemAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemAdminServiceImpl implements ISystemAdminService {

    private final IRoleService roleService;

    private final ISystemAdminRepository systemAdminRepository;

    private final ISystemAdminMapper systemAdminMapper;

    private final ExceptionShortComponent exceptionShortComponent;

    private final PasswordEncoder passwordEncoder;

    private final IJwtTokenSecurityService jwtTokenSecurityService;

    @Value("${role.default.admin}")
    private String systemAdminRole;

    @Override
    @Transactional
    public SystemAdminDTO createSystemAdmin(CreateSystemAdminVO createSystemAdminVO) {
        this.validateSystemAdminRequest(createSystemAdminVO, "system.admin.create.empty");

        try {
            String username = createSystemAdminVO.getUsername().trim();
            String email = createSystemAdminVO.getEmail().trim();

            this.validateUsernameUniqueness(username);
            this.validateEmailUniqueness(email);

            var roleEntity = this.roleService.findByName(this.systemAdminRole);

            UserEntity userEntity = this.systemAdminMapper.toEntity(createSystemAdminVO);
            userEntity.setPassword(this.passwordEncoder.encode(createSystemAdminVO.getPassword()));
            userEntity.setRoleEntity(roleEntity);

            return this.systemAdminMapper.toDto(this.systemAdminRepository.save(userEntity));
        } catch (AlreadyExistsException | ObjectNotFoundException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noCreatedException("system.admin.create.failed", createSystemAdminVO.getUsername());
        }
    }

    @Override
    @Transactional
    public SystemAdminDTO updateSystemAdmin(UpdateSystemAdminVO updateSystemAdminVO) {
        this.validateSystemAdminRequest(updateSystemAdminVO, "system.admin.update.empty");
        this.validateId(updateSystemAdminVO.getId());

        try {
            UserEntity existingAdmin = ((ISystemAdminService) AopContext.currentProxy()).getSystemAdminEntityById(updateSystemAdminVO.getId());

            String originalEmail = existingAdmin.getEmail();
            String originalUsername = existingAdmin.getUsername();

            if (updateSystemAdminVO.getStatus() != null) {
                existingAdmin.setStatus(Boolean.parseBoolean(updateSystemAdminVO.getStatus()));
            }

            this.systemAdminMapper.updateEntity(existingAdmin, updateSystemAdminVO);

            boolean sensitiveDataChanged = !originalEmail.equals(existingAdmin.getEmail()) ||
                    !originalUsername.equals(existingAdmin.getUsername());

            UserEntity updatedEditor = this.systemAdminRepository.save(existingAdmin);

            // If sensitive data changed, revoke all user tokens
            if (sensitiveDataChanged) {
                this.jwtTokenSecurityService.revokeAllUserTokens(updatedEditor.getUsername(),
                        "Profile updated with sensitive data change");
            }

            return this.systemAdminMapper.toDto(updatedEditor);
        } catch (ObjectNotFoundException | NoContentException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("system.admin.update.failed", updateSystemAdminVO.getId().toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SystemAdminDTO getSystemAdminById(Integer id) {
        this.validateId(id);

        try {
            UserEntity systemAdmin = ((ISystemAdminService) AopContext.currentProxy()).getSystemAdminEntityById(id);

            return this.systemAdminMapper.toDto(systemAdmin);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("system.admin.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SystemAdminDetailDTO getSystemAdminDetailById(Integer id) {
        this.validateId(id);

        try {
            UserEntity systemAdmin = ((ISystemAdminService) AopContext.currentProxy()).getSystemAdminEntityById(id);

            return this.systemAdminMapper.toDetailDto(systemAdmin);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("system.admin.detail.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getSystemAdminEntityById(Integer id) {
        this.validateId(id);

        try {
            var systemAdmin = this.systemAdminRepository.findById(id)
                    .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException("system.admin.not.found", id.toString()));
            var roleEntity = this.roleService.findByName(this.systemAdminRole);
            if (!systemAdmin.getRoleEntity().getId().equals(roleEntity.getId())) {
                throw this.exceptionShortComponent.objectNotFoundException("system.admin.invalid.role", id.toString());
            }

            return systemAdmin;
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("system.admin.get.entity.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SystemAdminDTO> searchSystemAdmins(SystemAdminFilterVO filterVO) {
        if (filterVO == null) {
            filterVO = new SystemAdminFilterVO();
        }

        try {
            var roleEntity = this.roleService.findByName(this.systemAdminRole);

            var pageable = PageRequest.of(
                    Math.clamp(filterVO.getPage(), 0, Integer.MAX_VALUE),
                    Math.clamp(filterVO.getSize(), 1, 100),
                    Sort.by(Sort.Direction.fromString(filterVO.getSortDirection()), filterVO.getSortBy())
            );

            return this.systemAdminRepository.searchSystemAdmins(filterVO.getUsername(), filterVO.getFirstName(),
                            filterVO.getLastName(), roleEntity.getId(), filterVO.getEnabled(), pageable)
                    .map(this.systemAdminMapper::toDto);
        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error searching system admins with filter: {}", filterVO, e);
            throw this.exceptionShortComponent.objectNotFoundException("system.admin.search.failed", filterVO.toString());
        }
    }

    @Override
    @Transactional
    public SystemAdminDTO changeSystemAdminStatus(Integer id, Boolean status) {
        this.validateId(id);
        if (status == null) {
            throw this.exceptionShortComponent.invalidParameterException("system.admin.status.empty");
        }

        try {
            UserEntity systemAdmin = ((ISystemAdminService) AopContext.currentProxy()).getSystemAdminEntityById(id);
            boolean statusChanged = !Objects.equals(systemAdmin.getStatus(), status);
            systemAdmin.setStatus(status);

            UserEntity updatedSystemAdmin = this.systemAdminRepository.save(systemAdmin);

            if (statusChanged && Boolean.TRUE.equals(!status)) {
                int revokedCount = this.jwtTokenSecurityService.revokeAllUserTokens(updatedSystemAdmin.getUsername(), "Account disabled");

                log.info("Revoked {} tokens for disabled user: {}",
                        revokedCount, systemAdmin.getUsername());
            }

            return this.systemAdminMapper.toDto(updatedSystemAdmin);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("system.admin.status.change.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("system.admin.username.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.systemAdminRole);
            return this.systemAdminRepository.existsByUsernameAndRoleId(username.trim(), roleEntity.getId());
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("system.admin.username.check.failed", username);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("system.admin.email.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.systemAdminRole);
            return this.systemAdminRepository.existsByEmailAndRoleId(email.trim(), roleEntity.getId());
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("system.admin.email.check.failed", email);
        }
    }

    @Override
    @Transactional
    public void deleteSystemAdmin(Integer id) {
        this.validateId(id);

        if (!this.systemAdminRepository.existsById(id)) {
            throw this.exceptionShortComponent.objectNotFoundException("system.admin.not.found", id.toString());
        }

        UserEntity systemAdmin = ((ISystemAdminService) AopContext.currentProxy()).getSystemAdminEntityById(id);
        try {
            int revokedCount = this.jwtTokenSecurityService.revokeAllUserTokens(systemAdmin.getUsername(), "Account deleted");

            log.info("Revoked {} tokens for deleted user: {}",
                    revokedCount, systemAdmin.getUsername());

            this.systemAdminRepository.delete(systemAdmin);
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noDeletedException("system.admin.delete.failed", id.toString());
        }
    }

    private void validateSystemAdminRequest(Object request, String messageKey) {
        if (request == null) {
            throw this.exceptionShortComponent.noContentException(messageKey);
        }
    }

    private void validateUsernameUniqueness(@NotNull String username) {
        if (this.systemAdminRepository.existsByUsername(username.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("system.admin.username.exists", username);
        }
    }

    private void validateEmailUniqueness(@NotNull String email) {
        if (this.systemAdminRepository.existsByEmail(email.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("system.admin.email.exists", email);
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("system.admin.id.invalid", String.valueOf(id));
        }
    }

}
