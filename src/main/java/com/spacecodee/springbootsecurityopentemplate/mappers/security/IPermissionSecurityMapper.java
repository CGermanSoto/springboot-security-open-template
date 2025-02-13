package com.spacecodee.springbootsecurityopentemplate.mappers.security;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.PermissionSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {
        IOperationSecurityMapper.class, IRoleSecurityMapper.class})
public interface IPermissionSecurityMapper {

     @Named("toUserDetailsPermissionDTOList")
    default List<PermissionSecurityDTO> toUserDetailsPermissionDTOList(Set<PermissionEntity> permissionEntities) {
        if (permissionEntities == null) {
            return Collections.emptyList();
        }
        return permissionEntities.stream()
                .map(this::toBasicUserDetailsPermissionDTO)
                .toList();
    }

    @Named("toBasicUserDetailsPermissionDTO")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "operationDTO", source = "operationEntity")
    PermissionSecurityDTO toBasicUserDetailsPermissionDTO(PermissionEntity permissionEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "operationDTO", source = "operationEntity")
    PermissionSecurityDTO toPermissionSecurityDTO(PermissionEntity permissionEntity);
}