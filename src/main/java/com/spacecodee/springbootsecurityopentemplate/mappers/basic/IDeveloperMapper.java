// IDeveloperMapper.java
package com.spacecodee.springbootsecurityopentemplate.mappers.basic;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperUVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;

import java.util.List;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IRoleMapper.class })
public interface IDeveloperMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleEntity", ignore = true)
    UserEntity voToEntity(DeveloperAVO developerAVO);

    @Mapping(target = "roleDTO", source = "roleEntity")
    DeveloperDTO toDto(UserEntity userEntity);

    List<DeveloperDTO> toDtoList(List<UserEntity> userEntities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity updateEntity(@MappingTarget UserEntity userEntity, DeveloperUVO developerUVO);
}