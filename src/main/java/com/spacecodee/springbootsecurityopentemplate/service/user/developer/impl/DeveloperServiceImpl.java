package com.spacecodee.springbootsecurityopentemplate.service.user.developer.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer.DeveloperDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.CreateDeveloperVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.UpdateDeveloperVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.mappers.user.developer.IDeveloperMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.user.IDeveloperRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtTokenSecurityService;
import com.spacecodee.springbootsecurityopentemplate.service.user.developer.IDeveloperService;
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
public class DeveloperServiceImpl implements IDeveloperService {

    private final IRoleService roleService;

    private final IDeveloperRepository developerRepository;

    private final IDeveloperMapper developerMapper;

    private final ExceptionShortComponent exceptionShortComponent;

    private final PasswordEncoder passwordEncoder;

    private final IJwtTokenSecurityService jwtTokenSecurityService;

    @Value("${role.default.developer}")
    private String developerRole;

    @Override
    @Transactional
    public DeveloperDTO createDeveloper(CreateDeveloperVO createDeveloperVO) {
        this.validateDeveloperRequest(createDeveloperVO, "developer.create.empty");

        try {
            String username = createDeveloperVO.getUsername().trim();
            String email = createDeveloperVO.getEmail().trim();

            this.validateUsernameUniqueness(username);
            this.validateEmailUniqueness(email);

            var roleEntity = this.roleService.findByName(this.developerRole);

            UserEntity userEntity = this.developerMapper.toEntity(createDeveloperVO);
            userEntity.setPassword(this.passwordEncoder.encode(createDeveloperVO.getPassword()));
            userEntity.setRoleEntity(roleEntity);

            return this.developerMapper.toDto(this.developerRepository.save(userEntity));
        } catch (AlreadyExistsException | ObjectNotFoundException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noCreatedException("developer.create.failed",
                    createDeveloperVO.getUsername());
        }
    }

    @Override
    @Transactional
    public DeveloperDTO updateDeveloper(UpdateDeveloperVO updateDeveloperVO) {
        this.validateDeveloperRequest(updateDeveloperVO, "developer.update.empty");
        this.validateId(updateDeveloperVO.getId());

        try {
            UserEntity existingDeveloper = ((IDeveloperService) AopContext.currentProxy())
                    .getDeveloperEntityById(updateDeveloperVO.getId());

            // Store original data for comparison
            String originalEmail = existingDeveloper.getEmail();
            String originalUsername = existingDeveloper.getUsername();

            if (updateDeveloperVO.getStatus() != null) {
                existingDeveloper.setStatus(Boolean.parseBoolean(updateDeveloperVO.getStatus()));
            }

            this.developerMapper.updateEntity(existingDeveloper, updateDeveloperVO);

            boolean sensitiveDataChanged = !originalEmail.equals(existingDeveloper.getEmail()) ||
                    !originalUsername.equals(existingDeveloper.getUsername());

            UserEntity updatedDeveloper = this.developerRepository.save(existingDeveloper);

            if (sensitiveDataChanged) {
                this.jwtTokenSecurityService.revokeAllUserTokens(updatedDeveloper.getUsername(),
                        "Profile updated with sensitive data change");
            }

            return this.developerMapper.toDto(updatedDeveloper);
        } catch (ObjectNotFoundException | NoContentException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("developer.update.failed",
                    updateDeveloperVO.getId().toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DeveloperDTO getDeveloperById(Integer id) {
        this.validateId(id);

        try {
            UserEntity developer = ((IDeveloperService) AopContext.currentProxy()).getDeveloperEntityById(id);

            return this.developerMapper.toDto(developer);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("developer.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DeveloperDetailDTO getDeveloperDetailById(Integer id) {
        this.validateId(id);

        try {
            UserEntity developer = ((IDeveloperService) AopContext.currentProxy()).getDeveloperEntityById(id);

            return this.developerMapper.toDetailDto(developer);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("developer.detail.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getDeveloperEntityById(Integer id) {
        this.validateId(id);

        try {
            var developerEntity = this.developerRepository.findById(id)
                    .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException("developer.not.found",
                            id.toString()));
            var roleEntity = this.roleService.findByName(this.developerRole);

            if (!developerEntity.getRoleEntity().getId().equals(roleEntity.getId())) {
                throw this.exceptionShortComponent.objectNotFoundException("developer.invalid.role", id.toString());
            }

            return developerEntity;
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("developer.get.entity.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeveloperDTO> searchDevelopers(DeveloperFilterVO filterVO) {
        if (filterVO == null) {
            filterVO = new DeveloperFilterVO();
        }

        try {
            var roleEntity = this.roleService.findByName(this.developerRole);
            var pageable = PageRequest.of(
                    Math.clamp(filterVO.getPage(), 0, Integer.MAX_VALUE),
                    Math.clamp(filterVO.getSize(), 1, 100),
                    Sort.by(Sort.Direction.fromString(filterVO.getSortDirection()),
                            filterVO.getSortBy()));
            return this.developerRepository.searchDevelopers(filterVO.getUsername(), filterVO.getFirstName(),
                            filterVO.getLastName(), roleEntity.getId(), filterVO.getEnabled(), pageable)
                    .map(this.developerMapper::toDto);
        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("developer.search.failed", filterVO.toString());
        }
    }

    @Override
    @Transactional
    public DeveloperDTO changeDeveloperStatus(Integer id, Boolean status) {
        this.validateId(id);
        if (status == null) {
            throw this.exceptionShortComponent.invalidParameterException("developer.status.empty");
        }

        try {
            UserEntity developer = ((IDeveloperService) AopContext.currentProxy()).getDeveloperEntityById(id);
            boolean statusChanged = !Objects.equals(developer.getStatus(), status);
            developer.setStatus(status);

            UserEntity updatedDeveloper = this.developerRepository.save(developer);

            if (statusChanged && Boolean.TRUE.equals(!status)) {
                int revokedCount = this.jwtTokenSecurityService.revokeAllUserTokens(
                        updatedDeveloper.getUsername(), "Account disabled");

                log.info("Revoked {} tokens for disabled user: {}",
                        revokedCount, updatedDeveloper.getUsername());
            }

            return this.developerMapper.toDto(updatedDeveloper);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("developer.status.change.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("developer.username.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.developerRole);
            return this.developerRepository.existsByUsernameAndRoleId(username.trim(), roleEntity.getId());
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("developer.username.check.failed", username);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("developer.email.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.developerRole);
            return this.developerRepository.existsByEmailAndRoleId(email.trim(), roleEntity.getId());
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("developer.email.check.failed", email);
        }
    }

    @Override
    @Transactional
    public void deleteDeveloper(Integer id) {
        this.validateId(id);

        if (!this.developerRepository.existsById(id)) {
            throw this.exceptionShortComponent.objectNotFoundException("developer.not.found", id.toString());
        }

        UserEntity developer = ((IDeveloperService) AopContext.currentProxy()).getDeveloperEntityById(id);
        try {
            int revokedCount = this.jwtTokenSecurityService.revokeAllUserTokens(
                    developer.getUsername(), "Account deleted");

            log.info("Revoked {} tokens for deleted user: {}",
                    revokedCount, developer.getUsername());

            this.developerRepository.delete(developer);
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noDeletedException("developer.delete.failed", id.toString());
        }
    }

    private void validateDeveloperRequest(Object request, String messageKey) {
        if (request == null) {
            throw this.exceptionShortComponent.noContentException(messageKey);
        }
    }

    private void validateUsernameUniqueness(@NotNull String username) {
        if (this.developerRepository.existsByUsername(username.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("developer.username.exists", username);
        }
    }

    private void validateEmailUniqueness(@NotNull String email) {
        if (this.developerRepository.existsByEmail(email.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("developer.email.exists", email);
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("developer.id.invalid", String.valueOf(id));
        }
    }
}
