package com.spacecodee.springbootsecurityopentemplate.service.core.permission.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.CreatePermissionVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.PermissionFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.UpdatePermissionVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.mappers.core.IPermissionMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.core.IPermissionRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.permission.IPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements IPermissionService {

    private final IPermissionRepository permissionRepository;
    private final IPermissionMapper permissionMapper;
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    @Transactional
    public PermissionDTO createPermission(CreatePermissionVO createPermissionVO) {
        this.validatePermissionRequest(createPermissionVO, "permission.create.empty");

        try {
            if (this.permissionRepository.existsByRoleIdAndOperationId(createPermissionVO.getRoleId(), createPermissionVO.getOperationId())) {
                throw this.exceptionShortComponent.alreadyExistsException("permission.role.operation.exists",
                        createPermissionVO.getRoleId().toString(), createPermissionVO.getOperationId().toString());
            }

            PermissionEntity permissionEntity = this.permissionMapper.toEntity(createPermissionVO);

            return this.permissionMapper.toDTO(this.permissionRepository.save(permissionEntity));
        } catch (AlreadyExistsException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noCreatedException("permission.create.failed",
                    createPermissionVO.getRoleId().toString(), createPermissionVO.getOperationId().toString()
            );
        }
    }

    @Override
    @Transactional
    public PermissionDTO updatePermission(UpdatePermissionVO updatePermissionVO) {
        this.validatePermissionRequest(updatePermissionVO, "permission.update.empty");
        this.validateId(updatePermissionVO.getId());

        try {
            PermissionEntity existingPermission = this.validateAndGetPermission(updatePermissionVO.getId());

            if (this.permissionRepository.existsByRoleIdAndOperationIdAndIdNot(
                    updatePermissionVO.getRoleId(), updatePermissionVO.getOperationId(), updatePermissionVO.getId())) {
                throw this.exceptionShortComponent.alreadyExistsException("permission.role.operation.exists",
                        updatePermissionVO.getRoleId().toString(), updatePermissionVO.getOperationId().toString()
                );
            }

            this.permissionMapper.updateEntity(existingPermission, updatePermissionVO);

            return this.permissionMapper.toDTO(this.permissionRepository.save(existingPermission));
        } catch (AlreadyExistsException | ObjectNotFoundException | NoContentException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("permission.update.failed", updatePermissionVO.getId().toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionDTO getPermissionById(Integer id) {
        this.validateId(id);

        try {
            return this.permissionMapper.toDTO(this.validateAndGetPermission(id));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("permission.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionDetailDTO getPermissionDetailById(Integer id) {
        this.validateId(id);

        try {
            return this.permissionMapper.toDetailDTO(this.validateAndGetPermission(id));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("permission.detail.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionDTO> searchPermissions(PermissionFilterVO filterVO) {
        if (filterVO == null) {
            filterVO = new PermissionFilterVO();
        }

        try {
            var pageable = PageRequest.of(
                    Math.clamp(filterVO.getPage(), 0, Integer.MAX_VALUE),
                    Math.clamp(filterVO.getSize(), 1, 100),
                    Sort.by(Sort.Direction.fromString(filterVO.getSortDirection()), filterVO.getSortBy())
            );

            return this.permissionRepository.findByFilters(filterVO.getRoleId(), filterVO.getOperationId(), pageable)
                    .map(this.permissionMapper::toDTO);

        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("permission.search.failed", filterVO.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO> getAllPermissionsByRoleId(Integer roleId) {
        this.validateId(roleId);

        try {
            return this.permissionRepository.findAllByRoleId(roleId).stream()
                    .map(this.permissionMapper::toDTO).toList();
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("permission.role.list.failed", roleId.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDetailDTO> getAllPermissionDetailsByRoleId(Integer roleId) {
        this.validateId(roleId);
        log.info("Getting all permission details by role id: {}", roleId);

        try {
            return this.permissionRepository.findAllByRoleId(roleId).stream()
                    .map(this.permissionMapper::toDetailDTO).toList();
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("permission.role.list.failed", roleId.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO> getAllPermissions() {
        try {
            return this.permissionRepository.findAll().stream().map(this.permissionMapper::toDTO).toList();
        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("permission.get.all.failed");
        }
    }

    @Override
    @Transactional
    public void deletePermission(Integer id) {
        this.validateId(id);

        try {
            this.permissionRepository.delete(this.validateAndGetPermission(id));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noDeletedException("permission.delete.failed", id.toString());
        }
    }

    private void validatePermissionRequest(Object request, String messageKey) {
        if (request == null) {
            throw this.exceptionShortComponent.noContentException(messageKey);
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("permission.id.invalid", String.valueOf(id));
        }
    }

    private PermissionEntity validateAndGetPermission(Integer id) {
        return this.permissionRepository.findById(id)
                .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException("permission.not.found", id.toString()));
    }
}
