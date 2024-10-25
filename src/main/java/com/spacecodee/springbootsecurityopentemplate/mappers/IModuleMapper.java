package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.ticklyspace.data.dto.ModuleDTO;
import com.spacecodee.ticklyspace.persistence.entity.ModuleEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IModuleMapper {
    ModuleEntity toEntity(ModuleDTO moduleDTO);

    ModuleDTO toDto(ModuleEntity moduleEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ModuleEntity partialUpdate(ModuleDTO moduleDTO, @MappingTarget ModuleEntity moduleEntity);
}