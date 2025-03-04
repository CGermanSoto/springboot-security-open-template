package com.spacecodee.springbootsecurityopentemplate.service.user.editor.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor.EditorDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor.EditorDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.CreateEditorVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.EditorFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.UpdateEditorVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.mappers.user.editor.IEditorMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.user.IEditorRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
import com.spacecodee.springbootsecurityopentemplate.service.security.token.IJwtTokenSecurityService;
import com.spacecodee.springbootsecurityopentemplate.service.user.editor.IEditorService;
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
public class EditorServiceImpl implements IEditorService {

    private final IRoleService roleService;

    private final IEditorRepository editorRepository;

    private final IEditorMapper editorMapper;

    private final ExceptionShortComponent exceptionShortComponent;

    private final PasswordEncoder passwordEncoder;

    private final IJwtTokenSecurityService jwtTokenSecurityService;

    @Value("${role.default.editor}")
    private String editorRole;

    @Override
    @Transactional
    public EditorDTO createEditor(CreateEditorVO createEditorVO) {
        this.validateEditorRequest(createEditorVO, "editor.create.empty");

        try {
            String username = createEditorVO.getUsername().trim();
            String email = createEditorVO.getEmail().trim();

            this.validateUsernameUniqueness(username);
            this.validateEmailUniqueness(email);

            var roleEntity = this.roleService.findByName(this.editorRole);

            UserEntity userEntity = this.editorMapper.toEntity(createEditorVO);
            userEntity.setPassword(this.passwordEncoder.encode(createEditorVO.getPassword()));
            userEntity.setRoleEntity(roleEntity);

            return this.editorMapper.toDto(this.editorRepository.save(userEntity));
        } catch (AlreadyExistsException | ObjectNotFoundException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error creating editor: {}", createEditorVO.getUsername(), e);
            throw this.exceptionShortComponent.noCreatedException("editor.create.failed", createEditorVO.getUsername());
        }
    }

    @Override
    @Transactional
    public EditorDTO updateEditor(UpdateEditorVO updateEditorVO) {
        this.validateEditorRequest(updateEditorVO, "editor.update.empty");
        this.validateId(updateEditorVO.getId());

        try {
            UserEntity existingEditor = ((IEditorService) AopContext.currentProxy()).getEditorEntityById(updateEditorVO.getId());

            String originalEmail = existingEditor.getEmail();
            String originalUsername = existingEditor.getUsername();

            if (updateEditorVO.getStatus() != null) {
                existingEditor.setStatus(Boolean.parseBoolean(updateEditorVO.getStatus()));
            }

            this.editorMapper.updateEntity(existingEditor, updateEditorVO);

            boolean sensitiveDataChanged = !originalEmail.equals(existingEditor.getEmail()) ||
                    !originalUsername.equals(existingEditor.getUsername());

            UserEntity updatedEditor = this.editorRepository.save(existingEditor);

            if (sensitiveDataChanged) {
                this.jwtTokenSecurityService.revokeAllUserTokens(updatedEditor.getUsername(),
                        "Profile updated with sensitive data change");
            }

            return this.editorMapper.toDto(updatedEditor);
        } catch (ObjectNotFoundException | NoContentException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("editor.update.failed", updateEditorVO.getId().toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EditorDTO getEditorById(Integer id) {
        this.validateId(id);

        try {
            UserEntity editorEntity = ((IEditorService) AopContext.currentProxy()).getEditorEntityById(id);

            return this.editorMapper.toDto(editorEntity);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("editor.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EditorDetailDTO getEditorDetailById(Integer id) {
        this.validateId(id);

        try {
            UserEntity editorEntity = ((IEditorService) AopContext.currentProxy()).getEditorEntityById(id);

            return this.editorMapper.toDetailDto(editorEntity);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("editor.detail.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getEditorEntityById(Integer id) {
        this.validateId(id);

        try {
            var userEntity = this.editorRepository.findById(id)
                    .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException("editor.not.found", id.toString()));

            var roleEntity = this.roleService.findByName(this.editorRole);
            if (!userEntity.getRoleEntity().getId().equals(roleEntity.getId())) {
                throw this.exceptionShortComponent.objectNotFoundException("editor.invalid.role", id.toString());
            }

            return userEntity;
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("editor.get.entity.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EditorDTO> searchEditors(EditorFilterVO filterVO) {
        if (filterVO == null) {
            filterVO = new EditorFilterVO();
        }

        try {
            var roleEntity = this.roleService.findByName(this.editorRole);

            var pageable = PageRequest.of(
                    Math.clamp(filterVO.getPage(), 0, Integer.MAX_VALUE),
                    Math.clamp(filterVO.getSize(), 1, 100),
                    Sort.by(Sort.Direction.fromString(filterVO.getSortDirection()), filterVO.getSortBy())
            );

            return this.editorRepository.searchEditors(filterVO.getUsername(), filterVO.getFirstName(),
                            filterVO.getLastName(), roleEntity.getId(), filterVO.getEnabled(), pageable)
                    .map(this.editorMapper::toDto);

        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("editor.search.failed", filterVO.toString());
        }
    }

    @Override
    @Transactional
    public EditorDTO changeEditorStatus(Integer id, Boolean status) {
        this.validateId(id);
        if (status == null) {
            throw this.exceptionShortComponent.invalidParameterException("editor.status.empty");
        }

        try {
            UserEntity editorEntity = ((IEditorService) AopContext.currentProxy()).getEditorEntityById(id);
            boolean statusChanged = !Objects.equals(editorEntity.getStatus(), status);
            editorEntity.setStatus(status);

            UserEntity updatedEditor = this.editorRepository.save(editorEntity);

            if (statusChanged && Boolean.TRUE.equals(!status)) {
                int revokedCount = this.jwtTokenSecurityService.revokeAllUserTokens(updatedEditor.getUsername(),
                        "Account disabled");

                log.info("Revoked {} tokens for disabled user: {}",
                        revokedCount, updatedEditor.getUsername());
            }

            return this.editorMapper.toDto(updatedEditor);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("editor.status.change.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("editor.username.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.editorRole);
            return this.editorRepository.existsByUsernameAndRoleId(username.trim(), roleEntity.getId());
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("editor.username.check.failed", username);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("editor.email.empty");
        }

        try {
            var roleEntity = this.roleService.findByName(this.editorRole);
            return this.editorRepository.existsByEmailAndRoleId(email.trim(), roleEntity.getId());
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("editor.email.check.failed", email);
        }
    }

    @Override
    @Transactional
    public void deleteEditor(Integer id) {
        this.validateId(id);

        if (!this.editorRepository.existsById(id)) {
            throw this.exceptionShortComponent.objectNotFoundException("editor.not.found", id.toString());
        }

        UserEntity editorEntity = ((IEditorService) AopContext.currentProxy()).getEditorEntityById(id);
        try {
            int revokedCount = this.jwtTokenSecurityService.revokeAllUserTokens(editorEntity.getUsername(), "Account deleted");

            log.info("Revoked {} tokens for deleted user: {}",
                    revokedCount, editorEntity.getUsername());

            this.editorRepository.delete(editorEntity);
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noDeletedException("editor.delete.failed", id.toString());
        }
    }

    private void validateEditorRequest(Object request, String messageKey) {
        if (request == null) {
            throw this.exceptionShortComponent.noContentException(messageKey);
        }
    }

    private void validateUsernameUniqueness(@NotNull String username) {
        if (this.editorRepository.existsByUsername(username.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("editor.username.exists", username);
        }
    }

    private void validateEmailUniqueness(@NotNull String email) {
        if (this.editorRepository.existsByEmail(email.trim())) {
            throw this.exceptionShortComponent.alreadyExistsException("editor.email.exists", email);
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("editor.id.invalid", String.valueOf(id));
        }
    }

}
