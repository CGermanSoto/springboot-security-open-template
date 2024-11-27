// IModuleDetailsMapper.java
package com.spacecodee.springbootsecurityopentemplate.mappers.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.ModuleEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IModuleDetailsMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "basePath", source = "basePath")
    UserDetailsModuleDTO toUserDetailsModuleDTO(ModuleEntity moduleEntity);
}