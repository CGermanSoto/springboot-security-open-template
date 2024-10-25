package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.ticklyspace.data.dto.RoleDTO;
import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsRoleDTO;
import com.spacecodee.ticklyspace.persistence.entity.RoleEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRoleMapper {
    RoleEntity toEntity(RoleDTO roleDTO);

    RoleDTO toDto(RoleEntity roleEntity);

    UserDetailsRoleDTO toUserDetailsRoleDTO(RoleEntity roleEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RoleEntity partialUpdate(RoleDTO roleDTO, @MappingTarget RoleEntity roleEntity);

    // Own
    @Named("toRoleEntity")
    @Mapping(target = "id", source = "roleId")
    RoleEntity toRoleEntity(Integer roleId);
}