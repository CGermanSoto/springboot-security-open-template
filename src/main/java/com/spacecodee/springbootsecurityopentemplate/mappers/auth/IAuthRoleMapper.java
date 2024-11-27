// IAuthRoleMapper.java
package com.spacecodee.springbootsecurityopentemplate.mappers.auth;

import com.spacecodee.springbootsecurityopentemplate.data.dto.RoleDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IAuthRoleMapper {

    @Named("toBasicUserDetailsRoleDTO")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RoleDTO toBasicUserDetailsRoleDTO(RoleEntity roleEntity);
}