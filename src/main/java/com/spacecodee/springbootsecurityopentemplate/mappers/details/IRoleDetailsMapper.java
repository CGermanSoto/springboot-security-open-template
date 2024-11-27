// RoleDetailsMapper.java
package com.spacecodee.springbootsecurityopentemplate.mappers.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsRoleDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IPermissionDetailsMapper.class })
public interface IRoleDetailsMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userDetailsPermissionDTOList", source = "permissionEntities", qualifiedByName = "toUserDetailsPermissionDTOList")
    UserDetailsRoleDTO toUserDetailsRoleDTO(RoleEntity roleEntity);
}