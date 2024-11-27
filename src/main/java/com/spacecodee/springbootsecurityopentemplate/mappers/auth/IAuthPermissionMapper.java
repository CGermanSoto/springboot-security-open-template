package com.spacecodee.springbootsecurityopentemplate.mappers.auth;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsPermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.mappers.details.IOperationDetailsMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IOperationDetailsMapper.class })
public interface IAuthPermissionMapper {

    @Named("toUserDetailsPermissionDTOList")
    default List<UserDetailsPermissionDTO> toUserDetailsPermissionDTOList(Set<PermissionEntity> permissionEntities) {
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
    UserDetailsPermissionDTO toBasicUserDetailsPermissionDTO(PermissionEntity permissionEntity);
}