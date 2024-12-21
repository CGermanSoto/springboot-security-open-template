package com.spacecodee.springbootsecurityopentemplate.mappers.basic;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.TechnicianDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianUVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

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