package com.spacecodee.springbootsecurityopentemplate.mappers.core;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.module.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.CreateModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.UpdateModuleVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.ModuleEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IModuleMapper {

    @Named("formatName")
    default String formatModuleName(String name) {
        return name != null ? name.trim().toUpperCase().replaceAll("\\s+", "_") : null;
    }

    @Named("trimPath")
    default String trimPath(String path) {
        return path != null ? path.trim() : null;
    }

    @Named("moduleIdToEntity")
    default ModuleEntity moduleIdToEntity(Integer id) {
        if (id == null)
            return null;
        ModuleEntity moduleEntity = new ModuleEntity();
        moduleEntity.setId(id);
        return moduleEntity;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "formatName")
    @Mapping(target = "basePath", source = "basePath", qualifiedByName = "trimPath")
    ModuleEntity toEntity(CreateModuleVO vo);

    @Mapping(target = "name", source = "name", qualifiedByName = "formatName")
    @Mapping(target = "basePath", source = "basePath", qualifiedByName = "trimPath")
    void updateEntity(@MappingTarget ModuleEntity entity, UpdateModuleVO vo);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "basePath", source = "basePath")
    ModuleDTO toDTO(ModuleEntity entity);

    List<ModuleDTO> toDTOList(List<ModuleEntity> entities);
}