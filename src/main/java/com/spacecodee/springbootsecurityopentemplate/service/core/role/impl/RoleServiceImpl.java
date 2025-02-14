package com.spacecodee.springbootsecurityopentemplate.service.core.role.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.role.RoleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.role.RoleDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.CreateRoleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.RoleFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.UpdateRoleVO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.mappers.core.IRoleMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.core.IRoleRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.role.IRoleService;
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
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;
    private final IRoleMapper roleMapper;
    private final ExceptionShortComponent exceptionShortComponent;

    private static final String ROLE_NAME_INVALID = "role.name.invalid";

    @Override
    @Transactional
    public RoleDTO createRole(CreateRoleVO createRoleVO) {
        this.validateRoleRequest(createRoleVO, "role.create.empty");

        try {
            this.validateRoleNameUniqueness(RoleEnum.valueOf(createRoleVO.getName().trim()), null);
            RoleEntity roleEntity = this.roleMapper.toEntity(createRoleVO);

            return this.roleMapper.toDTO(this.roleRepository.save(roleEntity));
        } catch (IllegalArgumentException e) {
            throw this.exceptionShortComponent.invalidParameterException(ROLE_NAME_INVALID, createRoleVO.getName());
        } catch (AlreadyExistsException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noCreatedException("role.create.failed", createRoleVO.getName());
        }
    }

    @Override
    @Transactional
    public RoleDTO updateRole(UpdateRoleVO updateRoleVO) {
        this.validateRoleRequest(updateRoleVO, "role.update.empty");
        this.validateId(updateRoleVO.getId());

        try {
            RoleEntity existingRole = this.validateAndGetRole(updateRoleVO.getId());
            this.validateRoleNameUniqueness(RoleEnum.valueOf(updateRoleVO.getName().trim()), updateRoleVO.getId());
            this.roleMapper.updateEntity(existingRole, updateRoleVO);

            return this.roleMapper.toDTO(this.roleRepository.save(existingRole));
        } catch (IllegalArgumentException e) {
            throw this.exceptionShortComponent.invalidParameterException(ROLE_NAME_INVALID, updateRoleVO.getName());
        } catch (AlreadyExistsException | ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("role.update.failed", String.valueOf(updateRoleVO.getId()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO getRoleById(Integer id) {
        this.validateId(id);

        try {
            return this.roleMapper.toDTO(this.validateAndGetRole(id));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("role.get.failed", String.valueOf(id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDetailDTO getRoleDetailById(Integer id) {
        this.validateId(id);

        try {
            return this.roleMapper.toDetailDTO(this.validateAndGetRole(id));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("role.detail.get.failed", String.valueOf(id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> searchRoles(RoleFilterVO filterVO) {
        if (filterVO == null) {
            filterVO = new RoleFilterVO();
        }

        try {
            var pageable = PageRequest.of(
                    Math.clamp(filterVO.getPage(), 0, Integer.MAX_VALUE),
                    Math.clamp(filterVO.getSize(), 1, 100),
                    Sort.by(Sort.Direction.fromString(filterVO.getSortDirection()),
                            filterVO.getSortBy()));
            String roleName = filterVO.getName() != null ? filterVO.getName().trim() : null;

            return this.roleRepository.findByFilters(roleName, pageable).map(this.roleMapper::toDTO);
        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("role.search.failed", filterVO.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getAllRoles() {
        try {
            return this.roleRepository.findAll().stream().map(this.roleMapper::toDTO).toList();
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("role.get.all.failed");
        }
    }

    @Override
    @Transactional()
    public void deleteRole(Integer id) {
        this.validateId(id);

        try {
            var roleEntity = this.validateAndGetRole(id);
            this.validateSystemRole(roleEntity.getName());

            this.roleRepository.delete(roleEntity);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noDeletedException("role.delete.failed", String.valueOf(id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RoleEntity findByName(String roleName) {
        if (roleName == null || roleName.isBlank()) {
            throw this.exceptionShortComponent.invalidParameterException("role.name.empty");
        }

        try {
            RoleEnum roleEnum = RoleEnum.valueOf(roleName.trim());
            return this.roleRepository.findByName(roleEnum)
                    .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException("role.not.found.by.name", roleName));
        } catch (IllegalArgumentException e) {
            throw this.exceptionShortComponent.invalidParameterException(ROLE_NAME_INVALID, roleName);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("role.find.by.name.failed", roleName);
        }
    }

    private void validateRoleRequest(Object request, String messageKey) {
        if (request == null) {
            throw this.exceptionShortComponent.noContentException(messageKey);
        }
    }

    private void validateRoleNameUniqueness(RoleEnum name, Integer roleId) {
        boolean exists = roleId == null
                ? this.roleRepository.existsByName(name)
                : this.roleRepository.existsByNameAndIdNot(name, roleId);

        if (exists) {
            throw this.exceptionShortComponent.alreadyExistsException("role.name.exists", name.name());
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("role.id.invalid", String.valueOf(id));
        }
    }

    private RoleEntity validateAndGetRole(Integer id) {
        return this.roleRepository.findById(id)
                .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException("role.not.found", String.valueOf(id)));
    }

    private void validateSystemRole(RoleEnum roleName) {
        if (roleName == RoleEnum.SYSTEM_ADMIN || roleName == RoleEnum.DEVELOPER) {
            throw this.exceptionShortComponent.noDeletedException("role.delete.system.role", roleName.name());
        }
    }
}
