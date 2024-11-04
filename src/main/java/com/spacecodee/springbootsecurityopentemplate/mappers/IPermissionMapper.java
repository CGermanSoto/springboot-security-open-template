package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.PermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsPermissionDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.PermissionEntity;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {IRoleMapper.class, IOperationMapper.class})
public interface IPermissionMapper {

    @Named("toUserDetailsPermissionDTOList")
    default List<UserDetailsPermissionDTO> toUserDetailsPermissionDTOList(Set<PermissionEntity> permissionEntities) {
        if (permissionEntities == null) {
            return Collections.emptyList();
        }

        List<UserDetailsPermissionDTO> list = new ArrayList<>(permissionEntities.size());
        for (PermissionEntity permissionEntity : permissionEntities) {
            list.add(toUserDetailsPermissionDTO(permissionEntity));
        }

        return list;
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "roleDTO", ignore = true)
    @Mapping(target = "operationDTO", source = "operationEntity")
    UserDetailsPermissionDTO toUserDetailsPermissionDTO(PermissionEntity permissionEntity);

    PermissionEntity toEntity(PermissionDTO permissionDTO);

    PermissionDTO toDto(PermissionEntity permissionEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PermissionEntity partialUpdate(PermissionDTO permissionDTO, @MappingTarget PermissionEntity permissionEntity);
}