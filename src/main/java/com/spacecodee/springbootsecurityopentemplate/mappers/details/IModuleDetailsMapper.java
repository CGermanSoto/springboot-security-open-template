// IModuleDetailsMapper.java
package com.spacecodee.springbootsecurityopentemplate.mappers.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserDetailsModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.ModuleEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IModuleDetailsMapper {

    @Named("mapModuleIdToModuleEntity")
    default ModuleEntity mapModuleIdToModuleEntity(Integer moduleId) {
        if (moduleId == null)
            return null;
        var moduleEntity = new ModuleEntity();
        moduleEntity.setId(moduleId);
        return moduleEntity;
    }

    @Named("mapModuleEntityToModuleId")
    default Integer mapModuleEntityToModuleId(ModuleEntity moduleEntity) {
        return moduleEntity != null ? moduleEntity.getId() : null;
    }

    @Mapping(target = "id", source = "id")
    UserDetailsModuleDTO toUserDetailsModuleDTO(ModuleEntity moduleEntity);

    @Mapping(target = "id", ignore = true)
    ModuleEntity dtoToEntity(ModuleVO moduleDTO);

    ModuleDTO toDTO(ModuleEntity moduleEntity);
}