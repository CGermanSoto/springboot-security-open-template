// PermissionDetailsMapper.java
package com.spacecodee.springbootsecurityopentemplate.mappers.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsPermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.mappers.auth.IAuthRoleMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IOperationDetailsMapper.class, IAuthRoleMapper.class})
public interface IPermissionDetailsMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "operationDTO", source = "operationEntity")
    UserDetailsPermissionDTO toUserDetailsPermissionDTO(PermissionEntity permissionEntity);

    @Mapping(target = "roleDTO", source = "roleEntity", qualifiedByName = "toBasicUserDetailsRoleDTO")
    @Mapping(target = "operationDTO", source = "operationEntity")
    PermissionDTO toDTO(PermissionEntity entity);
}