package com.spacecodee.springbootsecurityopentemplate.service.core.operation.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation.OperationDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.CreateOperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.OperationFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.UpdateOperationVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.mappers.core.IOperationMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.ModuleEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.OperationEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.core.IOperationRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.module.IModuleService;
import com.spacecodee.springbootsecurityopentemplate.service.core.operation.IOperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OperationServiceImpl implements IOperationService {

    private final IModuleService moduleService;
    private final IOperationRepository operationRepository;
    private final IOperationMapper operationMapper;
    private final ExceptionShortComponent exceptionShortComponent;

    @Override
    @Transactional
    public OperationDTO createOperation(CreateOperationVO createOperationVO) {
        this.validateOperationRequest(createOperationVO, "operation.create.empty");

        try {
            ModuleEntity moduleEntity = this.moduleService.getModuleEntityById(createOperationVO.getModuleId());

            this.validateTagUniqueness(createOperationVO.getTag(), createOperationVO.getModuleId(), null);
            this.validatePathAndMethodUniqueness(
                    createOperationVO.getPath(), createOperationVO.getHttpMethod(), createOperationVO.getModuleId()
            );

            OperationEntity operationEntity = this.operationMapper.toEntity(createOperationVO);
            operationEntity.setModuleEntity(moduleEntity);

            return this.operationMapper.toDTO(this.operationRepository.save(operationEntity));
        } catch (AlreadyExistsException | ObjectNotFoundException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noCreatedException("operation.create.failed", createOperationVO.getTag());
        }
    }

    @Override
    @Transactional
    public OperationDTO updateOperation(UpdateOperationVO updateOperationVO) {
        this.validateOperationRequest(updateOperationVO, "operation.update.empty");
        this.validateId(updateOperationVO.getId());

        try {
            OperationEntity existingOperation = this.validateAndGetOperation(updateOperationVO.getId());

            ModuleEntity moduleEntity = this.moduleService.getModuleEntityById(updateOperationVO.getModuleId());

            this.validateTagUniqueness(updateOperationVO.getTag(), updateOperationVO.getModuleId(), updateOperationVO.getId());
            this.validatePathAndMethodUniqueness(
                    updateOperationVO.getPath(), updateOperationVO.getHttpMethod(), updateOperationVO.getModuleId()
            );

            this.operationMapper.updateEntity(existingOperation, updateOperationVO);
            existingOperation.setModuleEntity(moduleEntity);

            return this.operationMapper.toDTO(this.operationRepository.save(existingOperation));
        } catch (AlreadyExistsException | ObjectNotFoundException | NoContentException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("operation.update.failed", updateOperationVO.getId().toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OperationDTO getOperationById(Integer id) {
        this.validateId(id);

        try {
            return this.operationMapper.toDTO(this.validateAndGetOperation(id));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("operation.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OperationDetailDTO getOperationDetailById(Integer id) {
        this.validateId(id);

        try {
            return this.operationMapper.toDetailDTO(this.validateAndGetOperation(id));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("operation.detail.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OperationDTO> searchOperations(OperationFilterVO filterVO) {
        if (filterVO == null) {
            filterVO = new OperationFilterVO();
        }

        try {
            var pageable = PageRequest.of(
                    Math.clamp(filterVO.getPage(), 0, Integer.MAX_VALUE),
                    Math.clamp(filterVO.getSize(), 1, 100),
                    Sort.by(Sort.Direction.fromString(filterVO.getSortDirection()), filterVO.getSortBy())
            );

            return this.operationRepository.findByFilters(
                    filterVO.getTag(),
                    filterVO.getPath(),
                    filterVO.getHttpMethod(),
                    filterVO.getPermitAll(),
                    filterVO.getModuleId(),
                    pageable).map(this.operationMapper::toDTO
            );

        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("operation.search.failed", filterVO.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperationDTO> getAllOperationsByModuleId(Integer moduleId) {
        this.validateId(moduleId);

        try {
            this.moduleService.getModuleEntityById(moduleId);

            return this.operationRepository.findByModuleEntity_Id(moduleId)
                    .stream().map(this.operationMapper::toDTO).toList();
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("operation.module.get.failed", String.valueOf(moduleId));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperationDTO> getAllOperations() {
        try {
            List<OperationEntity> operations = this.operationRepository.findAll();

            return operations.stream().map(this.operationMapper::toDTO).toList();

        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("operation.get.all.failed");
        }
    }

    @Override
    @Transactional
    public void deleteOperation(Integer id) {
        this.validateId(id);

        try {
            this.operationRepository.delete(this.validateAndGetOperation(id));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noDeletedException("operation.delete.failed", id.toString());
        }
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException("operation.id.invalid", String.valueOf(id));
        }
    }

    private void validateOperationRequest(Object request, String messageKey) {
        if (request == null) {
            throw this.exceptionShortComponent.noContentException(messageKey);
        }
    }

    private void validateTagUniqueness(String tag, Integer moduleId, Integer operationId) {
        boolean exists = operationId == null
                ? this.operationRepository.existsByTagAndModuleEntity_Id(tag.trim(), moduleId)
                : this.operationRepository.existsByTagAndModuleEntity_IdAndIdNot(tag.trim(), moduleId, operationId);

        if (exists) {
            throw this.exceptionShortComponent.alreadyExistsException("operation.tag.exists", tag);
        }
    }

    private void validatePathAndMethodUniqueness(@NotNull String path, @NotNull String httpMethod, Integer moduleId) {
        if (this.operationRepository.existsByPathAndHttpMethodAndModuleEntity_Id(
                path.trim(), httpMethod.trim().toUpperCase(), moduleId)) {
            throw this.exceptionShortComponent.alreadyExistsException("operation.path.method.exists", path, httpMethod);
        }
    }

    private OperationEntity validateAndGetOperation(Integer id) {
        return this.operationRepository.findById(id)
                .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException("operation.not.found", id.toString()));
    }
}
