package com.spacecodee.springbootsecurityopentemplate.service.core.module;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.module.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.CreateModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.ModuleFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.UpdateModuleVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.ModuleEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IModuleService {

    ModuleDTO createModule(CreateModuleVO createModuleVO);

    ModuleDTO updateModule(UpdateModuleVO updateModuleVO);

    ModuleDTO getModuleById(Integer id);

    ModuleEntity getModuleEntityById(Integer id);

    Page<ModuleDTO> searchModules(ModuleFilterVO filterVO);

    List<ModuleDTO> getAllModules();

    void deleteModule(Integer id);
}
