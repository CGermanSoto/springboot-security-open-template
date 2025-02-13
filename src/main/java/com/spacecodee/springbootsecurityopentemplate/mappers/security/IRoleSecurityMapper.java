package com.spacecodee.springbootsecurityopentemplate.mappers.security;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.RoleSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {
        IPermissionSecurityMapper.class })
public interface IRoleSecurityMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "permissionDTOList", source = "permissionEntities", qualifiedByName = "toUserDetailsPermissionDTOList")
    RoleSecurityDTO toRoleSecurityDTO(RoleEntity roleEntity);
}