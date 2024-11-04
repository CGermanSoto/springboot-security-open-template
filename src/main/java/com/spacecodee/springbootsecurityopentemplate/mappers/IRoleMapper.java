package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.RoleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsRoleDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {IPermissionMapper.class})
public interface IRoleMapper {
    RoleEntity toEntity(RoleDTO roleDTO);

    RoleDTO toDto(RoleEntity roleEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userDetailsPermissionDTOList", source = "permissionEntities", qualifiedByName = "toUserDetailsPermissionDTOList")
    UserDetailsRoleDTO toUserDetailsRoleDTO(RoleEntity roleEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RoleEntity partialUpdate(RoleDTO roleDTO, @MappingTarget RoleEntity roleEntity);

    // Own
    @Named("toRoleEntity")
    @Mapping(target = "id", source = "roleId")
    RoleEntity toRoleEntity(Integer roleId);
}