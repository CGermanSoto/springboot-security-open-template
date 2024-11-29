package com.spacecodee.springbootsecurityopentemplate.mappers.basic;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.RoleDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRoleMapper {
    // Basic CRUD mappings

    @Mapping(target = "permissionEntities", ignore = true)
    RoleEntity toEntity(RoleDTO roleDTO);

    @Mapping(target = "name", source = "name")
    RoleDTO toDto(RoleEntity roleEntity);
}