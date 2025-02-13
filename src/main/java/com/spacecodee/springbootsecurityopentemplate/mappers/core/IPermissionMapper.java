package com.spacecodee.springbootsecurityopentemplate.mappers.core;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.CreatePermissionVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission.UpdatePermissionVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {IRoleMapper.class,
        IOperationMapper.class})
public interface IPermissionMapper {

    @Mapping(target = "roleId", source = "roleEntity.id")
    @Mapping(target = "operationId", source = "operationEntity.id")
    PermissionDTO toDTO(PermissionEntity entity);

    @Mapping(target = "roleId", source = "roleEntity.id")
    @Mapping(target = "roleName", source = "roleEntity.name")
    @Mapping(target = "operationId", source = "operationEntity.id")
    @Mapping(target = "operationTag", source = "operationEntity.tag")
    @Mapping(target = "operationPath", source = "operationEntity.path")
    @Mapping(target = "operationHttpMethod", source = "operationEntity.httpMethod")
    PermissionDetailDTO toDetailDTO(PermissionEntity entity);

    List<PermissionDetailDTO> toDetailDTOList(List<PermissionEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleEntity", source = "roleId", qualifiedByName = "roleIdToEntity")
    @Mapping(target = "operationEntity", source = "operationId", qualifiedByName = "operationIdToEntity")
    PermissionEntity toEntity(CreatePermissionVO vo);

    @Mapping(target = "roleEntity", source = "roleId", qualifiedByName = "roleIdToEntity")
    @Mapping(target = "operationEntity", source = "operationId", qualifiedByName = "operationIdToEntity")
    PermissionEntity toEntity(UpdatePermissionVO vo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roleEntity", source = "roleId", qualifiedByName = "roleIdToEntity")
    @Mapping(target = "operationEntity", source = "operationId", qualifiedByName = "operationIdToEntity")
    void updateEntity(@MappingTarget PermissionEntity entity, UpdatePermissionVO vo);
}