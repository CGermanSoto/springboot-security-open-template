// IOperationDetailsMapper.java
package com.spacecodee.springbootsecurityopentemplate.mappers.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsOperationDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.OperationEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IModuleDetailsMapper.class })
public interface IOperationDetailsMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "tag", source = "tag")
    @Mapping(target = "path", source = "path")
    @Mapping(target = "httpMethod", source = "httpMethod")
    @Mapping(target = "permitAll", source = "permitAll")
    @Mapping(target = "moduleDTO", source = "moduleEntity")
    UserDetailsOperationDTO toUserDetailsOperationDTO(OperationEntity operationEntity);
}