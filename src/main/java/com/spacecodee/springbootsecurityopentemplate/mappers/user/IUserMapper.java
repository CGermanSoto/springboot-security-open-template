package com.spacecodee.springbootsecurityopentemplate.mappers.user;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.UserDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.UserDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.UserProfileDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserMapper {

    UserEntity toEntity(UserDTO viewerDTO);

    UserDTO toDTO(UserEntity userEntity);

    UserDetailDTO toDetailsDTO(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserDTO viewerDTO, @MappingTarget UserEntity userEntity);

    @Mapping(source = "roleEntity.name", target = "role")
    UserProfileDTO toProfileDTO(UserEntity userEntity);
}