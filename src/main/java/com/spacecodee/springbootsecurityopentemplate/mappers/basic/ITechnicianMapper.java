package com.spacecodee.springbootsecurityopentemplate.mappers.basic;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.TechnicianDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.TechnicianAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.TechnicianUVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IRoleMapper.class })
public interface ITechnicianMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleEntity", ignore = true)
    UserEntity voToEntity(TechnicianAVO technicianAVO);

    @Mapping(target = "roleDTO", source = "roleEntity")
    TechnicianDTO toDto(UserEntity userEntity);

    List<TechnicianDTO> toDtoList(List<UserEntity> userEntities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity updateEntity(@MappingTarget UserEntity userEntity, TechnicianUVO technicianUVO);
}