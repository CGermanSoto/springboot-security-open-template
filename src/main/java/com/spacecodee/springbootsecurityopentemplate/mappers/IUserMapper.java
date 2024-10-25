package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.UserDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.AddAdminVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {IRoleMapper.class})
public interface IUserMapper {
    UserEntity toEntity(UserDTO UserDTO);

    UserDTO toDto(UserEntity userEntity);

    UserDetailsDTO toUserDetailsDTO(UserEntity userEntity);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "roleEntity", ignore = true)
    UserEntity toEntity(AddAdminVO addAdminVO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserDTO UserDTO, @MappingTarget UserEntity userEntity);
}