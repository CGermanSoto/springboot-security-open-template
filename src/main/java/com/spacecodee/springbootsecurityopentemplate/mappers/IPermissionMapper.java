package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {IRoleMapper.class, IOperationMapper.class})
public interface IPermissionMapper {
    PermissionEntity toEntity(PermissionDTO permissionDTO);

    PermissionDTO toDto(PermissionEntity permissionEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PermissionEntity partialUpdate(PermissionDTO permissionDTO, @MappingTarget PermissionEntity permissionEntity);
}