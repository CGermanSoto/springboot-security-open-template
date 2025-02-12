package com.spacecodee.springbootsecurityopentemplate.mappers.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.RoleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.RoleSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.mappers.auth.IAuthPermissionMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IAuthPermissionMapper.class })
public interface IRoleDetailsMapper {
    // Authentication specific mappings

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "permissionSecurityDTOList", source = "permissionEntities", qualifiedByName = "toUserDetailsPermissionDTOList")
    RoleSecurityDTO toUserDetailsRoleDTO(RoleEntity roleEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RoleDTO toDTO(RoleEntity entity);
}