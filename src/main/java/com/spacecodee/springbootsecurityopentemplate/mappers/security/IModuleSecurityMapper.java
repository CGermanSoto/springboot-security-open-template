package com.spacecodee.springbootsecurityopentemplate.mappers.security;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.ModuleSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.ModuleEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IModuleSecurityMapper {

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
    ModuleSecurityDTO toUserDetailsModuleDTO(ModuleEntity moduleEntity);
}