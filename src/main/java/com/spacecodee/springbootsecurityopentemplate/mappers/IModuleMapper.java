package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.module.ModuleVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.ModuleEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IModuleMapper {

    @Named("mapModuleIdToModuleEntity")
    default ModuleEntity mapModuleIdToModuleEntity(int userId) {
        var userEntity = new ModuleEntity();
        userEntity.setId(userId);
        return userEntity;
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "basePath", source = "basePath")
    UserDetailsModuleDTO toUserDetailsModuleDTO(ModuleEntity moduleEntity);

    ModuleEntity toEntity(ModuleDTO moduleDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "basePath", source = "basePath")
    ModuleEntity dtoToEntity(ModuleVO moduleDTO);

    ModuleDTO toDto(ModuleEntity moduleEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ModuleEntity partialUpdate(ModuleDTO moduleDTO, @MappingTarget ModuleEntity moduleEntity);
}