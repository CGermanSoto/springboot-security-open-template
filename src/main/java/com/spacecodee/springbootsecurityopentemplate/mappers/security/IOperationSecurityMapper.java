package com.spacecodee.springbootsecurityopentemplate.mappers.security;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.OperationSecurityPathDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.OperationEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {
        IModuleSecurityMapper.class})
public interface IOperationSecurityMapper {

    @Mapping(target = "moduleSecurityDTO", source = "moduleEntity")
    OperationSecurityDTO toOperationSecurityDTO(OperationEntity operationEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "tag", source = "tag")
    @Mapping(target = "path", source = "path")
    @Mapping(target = "httpMethod", source = "httpMethod")
    @Mapping(target = "permitAll", source = "permitAll")
    @Mapping(target = "moduleSecurityDTO", ignore = true)
    OperationSecurityDTO toOperationSecurityDTO(OperationSecurityDTO operationDTO);

    @Mapping(target = "fullPath", source = ".", qualifiedByName = "buildFullPath")
    OperationSecurityPathDTO toOperationSecurityPathDTO(OperationEntity operationEntity);

    @Named("buildFullPath")
    default String buildFullPath(OperationEntity operationEntity) {
        if (operationEntity == null || operationEntity.getModuleEntity() == null) {
            return "";
        }
        return operationEntity.getModuleEntity().getBasePath() + operationEntity.getPath();
    }

}