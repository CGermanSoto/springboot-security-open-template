package com.spacecodee.springbootsecurityopentemplate.service.core.module.impl;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.module.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.CreateModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.ModuleFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.UpdateModuleVO;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.ObjectNotFoundException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.operation.NoContentException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.util.ExceptionShortComponent;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.AlreadyExistsException;
import com.spacecodee.springbootsecurityopentemplate.exceptions.validation.InvalidParameterException;
import com.spacecodee.springbootsecurityopentemplate.mappers.core.IModuleMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.ModuleEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.repository.core.IModuleRepository;
import com.spacecodee.springbootsecurityopentemplate.service.core.module.IModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements IModuleService {

    private final IModuleRepository moduleRepository;
    private final IModuleMapper moduleMapper;
    private final ExceptionShortComponent exceptionShortComponent;

    private static final String MODULE_ID_INVALID = "module.id.invalid";
    private static final String MODULE_NOT_FOUND = "module.not.found";

    @Override
    @Transactional
    public ModuleDTO createModule(CreateModuleVO createModuleVO) {
        if (createModuleVO == null) {
            throw this.exceptionShortComponent.noContentException("module.create.empty");
        }

        try {
            if (this.moduleRepository.existsByNameIgnoreCase(createModuleVO.getName().trim())) {
                throw this.exceptionShortComponent.alreadyExistsException("module.name.exists", createModuleVO.getName());
            }

            if (this.moduleRepository.existsByBasePathIgnoreCase(createModuleVO.getBasePath().trim())) {
                throw this.exceptionShortComponent.alreadyExistsException("module.base.path.exists", createModuleVO.getBasePath());
            }

            ModuleEntity moduleEntity = this.moduleMapper.toEntity(createModuleVO);

            return this.moduleMapper.toDTO(this.moduleRepository.save(moduleEntity));
        } catch (AlreadyExistsException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error creating module: {}", createModuleVO.getName(), e);
            throw this.exceptionShortComponent.noCreatedException("module.create.failed", createModuleVO.getName());
        }
    }

    @Override
    @Transactional
    public ModuleDTO updateModule(UpdateModuleVO updateModuleVO) {
        if (updateModuleVO == null || updateModuleVO.getId() == null) {
            throw this.exceptionShortComponent.noContentException("module.update.empty");
        }

        try {
            ModuleEntity existingModule = this.moduleRepository.findById(updateModuleVO.getId())
                    .orElseThrow(() -> this.exceptionShortComponent
                            .objectNotFoundException(MODULE_NOT_FOUND, updateModuleVO.getId().toString()));

            if (this.moduleRepository.existsByNameIgnoreCaseAndIdNot(updateModuleVO.getName().trim(), updateModuleVO.getId())) {
                throw this.exceptionShortComponent.alreadyExistsException("module.exists.by.name", updateModuleVO.getName());
            }

            if (this.moduleRepository.existsByBasePathIgnoreCaseAndIdNot(updateModuleVO.getBasePath().trim(), updateModuleVO.getId())) {
                throw this.exceptionShortComponent.alreadyExistsException("module.exists.by.path", updateModuleVO.getBasePath());
            }

            this.moduleMapper.updateEntity(existingModule, updateModuleVO);

            return this.moduleMapper.toDTO(this.moduleRepository.save(existingModule));
        } catch (ObjectNotFoundException | AlreadyExistsException | NoContentException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noUpdatedException("module.update.failed", updateModuleVO.getId().toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleDTO getModuleById(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(
                    MODULE_ID_INVALID, String.valueOf(id));
        }

        try {
            return this.moduleRepository.findById(id).map(this.moduleMapper::toDTO)
                    .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException(MODULE_NOT_FOUND, id.toString()));
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("module.get.failed", id.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleEntity getModuleEntityById(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(MODULE_ID_INVALID, String.valueOf(id));
        }

        return this.moduleRepository.findById(id)
                .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException(MODULE_NOT_FOUND, id.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModuleDTO> searchModules(ModuleFilterVO filterVO) {
        if (filterVO == null) {
            filterVO = new ModuleFilterVO();
        }

        try {
            String sortDirection = filterVO.getSortDirection().toUpperCase();
            if (!sortDirection.equals("ASC") && !sortDirection.equals("DESC")) {
                throw this.exceptionShortComponent
                        .invalidParameterException("module.search.invalid.sort.direction", filterVO.getSortDirection());
            }

            var pageable = PageRequest.of(
                    Math.clamp(filterVO.getPage(), 0, Integer.MAX_VALUE),
                    Math.clamp(filterVO.getSize(), 1, 100),
                    Sort.by(Sort.Direction.fromString(sortDirection), filterVO.getSortBy())
            );

            return this.moduleRepository.findByFilters(
                    filterVO.getName(),
                    filterVO.getBasePath(),
                    pageable).map(this.moduleMapper::toDTO);

        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("module.search.failed", filterVO.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleDTO> getAllModules() {
        try {
            return this.moduleRepository.findAll().stream().map(this.moduleMapper::toDTO).toList();
        } catch (Exception e) {
            throw this.exceptionShortComponent.objectNotFoundException("module.get.all.failed");
        }
    }

    @Override
    @Transactional
    public void deleteModule(Integer id) {
        if (id == null || id <= 0) {
            throw this.exceptionShortComponent.invalidParameterException(MODULE_ID_INVALID, String.valueOf(id));
        }

        try {
            ModuleEntity module = this.moduleRepository.findById(id)
                    .orElseThrow(() -> this.exceptionShortComponent.objectNotFoundException(MODULE_NOT_FOUND, id.toString()));

            this.moduleRepository.delete(module);
        } catch (ObjectNotFoundException | InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw this.exceptionShortComponent.noDeletedException("module.delete.failed", id.toString());
        }
    }
}
