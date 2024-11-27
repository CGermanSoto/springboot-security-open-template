// PermissionDetailsMapper.java
package com.spacecodee.springbootsecurityopentemplate.mappers.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsPermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IOperationDetailsMapper.class, IRoleDetailsMapper.class})
public interface IPermissionDetailsMapper {

    @Named("toUserDetailsPermissionDTOList")
    default List<UserDetailsPermissionDTO> toUserDetailsPermissionDTOList(Set<PermissionEntity> permissionEntities) {
        if (permissionEntities == null) {
            return Collections.emptyList();
        }

        return permissionEntities.stream()
                .map(this::toUserDetailsPermissionDTO)
                .toList();
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "operationDTO", source = "operationEntity")
    UserDetailsPermissionDTO toUserDetailsPermissionDTO(PermissionEntity permissionEntity);

    @Mapping(target = "roleDTO", source = "roleEntity")
    @Mapping(target = "operationDTO", source = "operationEntity")
    PermissionDTO toDTO(PermissionEntity entity);
}