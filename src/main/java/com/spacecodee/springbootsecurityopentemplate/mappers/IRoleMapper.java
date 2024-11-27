package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.RoleDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRoleMapper {

    @Mapping(target = "permissionEntities", ignore = true)
    RoleEntity toEntity(RoleDTO roleDTO);

    @Mapping(target = "name", source = "name")
    RoleDTO toDto(RoleEntity roleEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RoleEntity partialUpdate(RoleDTO roleDTO, @MappingTarget RoleEntity roleEntity);

    @Named("toRoleEntity")
    @Mapping(target = "id", source = "roleId")
    @Mapping(target = "permissionEntities", ignore = true)
    RoleEntity toRoleEntity(Integer roleId);
}