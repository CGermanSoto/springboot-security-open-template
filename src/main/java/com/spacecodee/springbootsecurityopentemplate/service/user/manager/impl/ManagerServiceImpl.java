package com.spacecodee.springbootsecurityopentemplate.service.user.manager.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager.ManagerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager.ManagerDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.CreateManagerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.ManagerFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.UpdateManagerVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.mappers.user.manager.IManagerMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.user.IManagerRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.user.manager.IManagerService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements IManagerService {

    private final IRoleService roleService;
    private final IManagerRepository managerRepository;
    private final IManagerMapper managerMapper;
    private final ExceptionShortComponent exceptionShortComponent;
    private final PasswordEncoder passwordEncoder;

    @Value("${role.default.manager}")
    private String managerRole;

    @Override
    @Transactional
    public ManagerDTO createManager(CreateManagerVO createManagerVO) {
        this.validateManagerRequest(createManagerVO, "manager.create.empty");

        try {
            String username = createManagerVO.getUsername().trim();
            String email = createManagerVO.getEmail().trim();

            this.validateUsernameUniqueness(username);
            this.validateEmailUniqueness(email);

            var roleEntity = this.roleService.findByName(this.managerRole);

            UserEntity userEntity = this.managerMapper.toEntity(createManagerVO);
            userEntity.setPassword(this.passwordEncoder.encode(createManagerVO.getPassword()));
            userEntity.setRoleEntity(roleEntity);

            return this.managerMapper.toDto(this.managerRepository.save(userEntity));
        } catch (AlreadyExistsException | ObjectNotFoundException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noCreatedException("manager.create.failed", createManagerVO.getUsername());
        }
    }

    @Override
    @Transactional
    public ManagerDTO updateManager(UpdateManagerVO updateManagerVO) {
        this.validateManagerRequest(updateManagerVO, "manager.update.empty");
        this.validateId(updateManagerVO.getId());

        try {
            UserEntity existingManager = ((IManagerService) AopContext.currentProxy()).getManagerEntityById(updateManagerVO.getId());

            if (updateManagerVO.getStatus() != null) {
                existingManager.setStatus(Boolean.parseBoolean(updateManagerVO.getStatus()));
            }
            this.managerMapper.updateEntity(existingManager, updateManagerVO);

            return this.managerMapper.toDto(this.managerRepository.save(existingManager));
        } catch (ObjectNotFoundException | NoContentException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("manager.update.failed", updateManagerVO.getId().toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ManagerDTO getManagerById(Integer id) {
        this.validateId(id);

        try {
            UserEntity managerEntity = ((IManagerService) AopContext.currentProxy()).getManagerEntityById(id);

            return this.managerMapper.toDto(managerEntity);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("manager.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ManagerDetailDTO getManagerDetailById(Integer id) {
        this.validateId(id);

        try {
            UserEntity managerEntity = ((IManagerService) AopContext.currentProxy()).getManagerEntityById(id);

            return this.managerMapper.toDetailDto(managerEntity);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("manager.detail.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getManagerEntityById(Integer id) {
        this.validateId(id);

        try {
            var managerEntity = this.managerRepository.findById(id)
                    .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException("manager.not.found", id.toString()));

            var roleEntity = this.roleService.findByName(this.managerRole);
            if (!managerEntity.getRoleEntity().getId().equals(roleEntity.getId())) {
                throw this.exceptionShortComponent.objectNotFoundException("manager.invalid.role", id.toString());
            }

            return managerEntity;
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("manager.get.entity.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManagerDTO> searchManagers(ManagerFilterVO filterVO) {
        if (filterVO == null) {
            filterVO = new ManagerFilterVO();
        }

        try {
            var roleEntity = this.roleService.findByName(this.managerRole);
            var pageable = PageRequest.of(
                    Math.clamp(filterVO.getPage(), 0, Integer.MAX_VALUE),
                    Math.clamp(filterVO.getSize(), 1, 100),
                    Sort.by(Sort.Direction.fromString(filterVO.getSortDirection()), filterVO.getSortBy())
            );

            return this.managerRepository.searchManagers(filterVO.getUsername(), filterVO.getFirstName(),
                            filterVO.getLastName(), roleEntity.getId(), filterVO.getEnabled(), pageable)
                    .map(this.managerMapper::toDto);
        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("manager.search.failed", filterVO.toString());
        }
    }

    @Override
    @Transactional
    public ManagerDTO changeManagerStatus(Integer id, Boolean status) {
        this.validateId(id);
        if (status == null) {
            throw this.exceptionShortComponent.invalidParameterException("manager.status.empty");
        }

        try {
            UserEntity managerEntity = ((IManagerService) AopContext.currentProxy()).getManagerEntityById(id);
            managerEntity.setStatus(status);

            return this.managerMapper.toDto(this.managerRepository.save(managerEntity));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("manager.status.change.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("manager.username.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.managerRole);
            return this.managerRepository.existsByUsernameAndRoleId(username.trim(), roleEntity.getId());
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("manager.username.check.failed", username);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("manager.email.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.managerRole);
            return this.managerRepository.existsByEmailAndRoleId(email.trim(), roleEntity.getId());
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("manager.email.check.failed", email);
        }
    }

    @Override
    @Transactional
    public void deleteManager(Integer id) {
        this.validateId(id);

        if (!this.managerRepository.existsById(id)) {
            throw this.exceptionShortComponent.objectNotFoundException("manager.not.found", id.toString());
        }

        UserEntity managerEntity = ((IManagerService) AopContext.currentProxy()).getManagerEntityById(id);
        try {
            this.managerRepository.delete(managerEntity);
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noDeletedException("manager.delete.failed", id.toString());
        }
    }

    private void validateManagerRequest(Object request, String messageKey) {
        if (request == null) {
            throw this.exceptionShortComponent.noContentException(messageKey);
        }
    }

    private void validateUsernameUniqueness(@NotNull String username) {
        if (this.managerRepository.existsByUsername(username.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("manager.username.exists", username);
        }
    }

    private void validateEmailUniqueness(@NotNull String email) {
        if (this.managerRepository.existsByEmail(email.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("manager.email.exists", email);
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("manager.id.invalid", String.valueOf(id));
        }
    }

}
