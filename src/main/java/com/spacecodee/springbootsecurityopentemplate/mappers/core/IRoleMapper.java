package com.spacecodee.springbootsecurityopentemplate.mappers.core;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.role.RoleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.role.RoleDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.CreateRoleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.role.UpdateRoleVO;
import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IRoleMapper {

    @Mapping(target = "name", expression = "java(entity.getName().name())")
    RoleDTO toDTO(RoleEntity entity);

    @Mapping(target = "name", expression = "java(entity.getName().name())")
    @Mapping(target = "permissions", expression = "java(mapPermissions(entity))")
    RoleDetailDTO toDetailDTO(RoleEntity entity);

    List<RoleDTO> toDTOList(List<RoleEntity> entities);

    List<RoleDetailDTO> toDetailDTOList(List<RoleEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "stringToRoleEnum")
    @Mapping(target = "permissionEntities", ignore = true)
    RoleEntity toEntity(CreateRoleVO vo);

    @Mapping(target = "name", source = "name", qualifiedByName = "stringToRoleEnum")
    @Mapping(target = "permissionEntities", ignore = true)
    void updateEntity(@MappingTarget RoleEntity entity, UpdateRoleVO vo);

    @Named("mapPermissions")
    default Set<String> mapPermissions(@NotNull RoleEntity entity) {
        return entity.getPermissionEntities().stream()
                .map(permission -> permission.getOperationEntity().getTag())
                .collect(java.util.stream.Collectors.toSet());
    }

    @Named("stringToRoleEnum")
    default RoleEnum stringToRoleEnum(String name) {
        return RoleEnum.valueOf(name);
    }

    @Named("roleIdToEntity")
    default RoleEntity roleIdToEntity(Integer roleId) {
        if (roleId == null) return null;
        return new RoleEntity().setId(roleId);
    }
}