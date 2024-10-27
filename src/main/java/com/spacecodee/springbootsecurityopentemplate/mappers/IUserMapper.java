package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.UserDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {IRoleMapper.class})
public interface IUserMapper {
    UserEntity toEntity(UserDTO UserDTO);

    UserDTO toDto(UserEntity userEntity);

    UserDetailsDTO toUserDetailsDTO(UserEntity userEntity);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "roleEntity", ignore = true)
    UserEntity toEntity(AdminVO adminVO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserDTO UserDTO, @MappingTarget UserEntity userEntity);
}