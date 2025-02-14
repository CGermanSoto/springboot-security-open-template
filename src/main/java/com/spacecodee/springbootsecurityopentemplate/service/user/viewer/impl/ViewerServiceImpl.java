package com.spacecodee.springbootsecurityopentemplate.service.user.viewer.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer.ViewerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer.ViewerDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.CreateViewerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.UpdateViewerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.ViewerFilterVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.mappers.user.viewer.IViewerMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.user.IViewerRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.user.viewer.IViewerService;
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
public class ViewerServiceImpl implements IViewerService {

    private final IRoleService roleService;
    private final IViewerRepository viewerRepository;
    private final IViewerMapper viewerMapper;
    private final ExceptionShortComponent exceptionShortComponent;
    private final PasswordEncoder passwordEncoder;

    @Value("${role.default.viewer}")
    private String viewerRole;

    @Override
    @Transactional
    public ViewerDTO createViewer(CreateViewerVO createViewerVO) {
        this.validateViewerRequest(createViewerVO, "viewer.create.empty");

        try {
            String username = createViewerVO.getUsername().trim();
            String email = createViewerVO.getEmail().trim();

            this.validateUsernameUniqueness(username);
            this.validateEmailUniqueness(email);

            var roleEntity = this.roleService.findByName(this.viewerRole);

            UserEntity userEntity = this.viewerMapper.toEntity(createViewerVO);
            userEntity.setPassword(this.passwordEncoder.encode(createViewerVO.getPassword()));
            userEntity.setRoleEntity(roleEntity);

            return this.viewerMapper.toDto(this.viewerRepository.save(userEntity));
        } catch (AlreadyExistsException | ObjectNotFoundException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noCreatedException("viewer.create.failed", createViewerVO.getUsername());
        }
    }

    @Override
    @Transactional
    public ViewerDTO updateViewer(UpdateViewerVO updateViewerVO) {
        this.validateViewerRequest(updateViewerVO, "viewer.update.empty");
        this.validateId(updateViewerVO.getId());

        try {
            UserEntity existingViewer = ((IViewerService) AopContext.currentProxy()).getViewerEntityById(updateViewerVO.getId());
            if (updateViewerVO.getStatus() != null) {
                existingViewer.setStatus(Boolean.parseBoolean(updateViewerVO.getStatus()));
            }
            this.viewerMapper.updateEntity(existingViewer, updateViewerVO);

            return this.viewerMapper.toDto(this.viewerRepository.save(existingViewer));
        } catch (ObjectNotFoundException | NoContentException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("viewer.update.failed", updateViewerVO.getId().toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ViewerDTO getViewerById(Integer id) {
        this.validateId(id);

        try {
            UserEntity viewerEntity = ((IViewerService) AopContext.currentProxy()).getViewerEntityById(id);

            return this.viewerMapper.toDto(viewerEntity);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("viewer.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ViewerDetailDTO getViewerDetailById(Integer id) {
        this.validateId(id);

        try {
            UserEntity viewer = ((IViewerService) AopContext.currentProxy()).getViewerEntityById(id);

            return this.viewerMapper.toDetailDto(viewer);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("viewer.detail.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getViewerEntityById(Integer id) {
        this.validateId(id);

        try {
            var viewer = this.viewerRepository.findById(id)
                    .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException("viewer.not.found", id.toString()));

            var roleEntity = this.roleService.findByName(this.viewerRole);
            if (!viewer.getRoleEntity().getId().equals(roleEntity.getId())) {
                throw this.exceptionShortComponent.objectNotFoundException("viewer.invalid.role", id.toString());
            }

            return viewer;
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("viewer.get.entity.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ViewerDTO> searchViewers(ViewerFilterVO filterVO) {
        if (filterVO == null) {
            filterVO = new ViewerFilterVO();
        }

        try {
            var roleEntity = this.roleService.findByName(this.viewerRole);
            var pageable = PageRequest.of(
                    Math.clamp(filterVO.getPage(), 0, Integer.MAX_VALUE),
                    Math.clamp(filterVO.getSize(), 1, 100),
                    Sort.by(Sort.Direction.fromString(filterVO.getSortDirection()), filterVO.getSortBy())
            );

            return this.viewerRepository.searchViewers(filterVO.getUsername(), filterVO.getFirstName(),
                            filterVO.getLastName(), roleEntity.getId(), filterVO.getEnabled(), pageable)
                    .map(this.viewerMapper::toDto);

        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("viewer.search.failed", filterVO.toString());
        }
    }

    @Override
    @Transactional
    public ViewerDTO changeViewerStatus(Integer id, Boolean status) {
        this.validateId(id);
        if (status == null) {
            throw this.exceptionShortComponent.invalidParameterException("viewer.status.empty");
        }

        try {
            UserEntity viewerEntity = ((IViewerService) AopContext.currentProxy()).getViewerEntityById(id);
            viewerEntity.setStatus(status);

            return this.viewerMapper.toDto(this.viewerRepository.save(viewerEntity));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("viewer.status.change.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("viewer.username.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.viewerRole);
            return this.viewerRepository.existsByUsernameAndRoleId(username.trim(), roleEntity.getId());
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("viewer.username.check.failed", username);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("viewer.email.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.viewerRole);
            return this.viewerRepository.existsByEmailAndRoleId(email.trim(), roleEntity.getId());
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("viewer.email.check.failed", email);
        }
    }

    @Override
    @Transactional
    public void deleteViewer(Integer id) {
        this.validateId(id);

        if (!this.viewerRepository.existsById(id)) {
            throw this.exceptionShortComponent.objectNotFoundException("viewer.not.found", id.toString());
        }

        UserEntity viewerEntity = ((IViewerService) AopContext.currentProxy()).getViewerEntityById(id);
        try {
            this.viewerRepository.delete(viewerEntity);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noDeletedException("viewer.delete.failed", id.toString());
        }
    }

    private void validateViewerRequest(Object request, String messageKey) {
        if (request == null) {
            throw this.exceptionShortComponent.noContentException(messageKey);
        }
    }

    private void validateUsernameUniqueness(@NotNull String username) {
        if (this.viewerRepository.existsByUsername(username.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("viewer.username.exists", username);
        }
    }

    private void validateEmailUniqueness(@NotNull String email) {
        if (this.viewerRepository.existsByEmail(email.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("viewer.email.exists", email);
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("viewer.id.invalid", String.valueOf(id));
        }
    }

}
