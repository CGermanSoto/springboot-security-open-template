package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.UserDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {IRoleMapper.class})
public interface IUserMapper {

    @Named("mapUserIdToUserEntity")
    default UserEntity mapUserIdToUserEntity(int userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return userEntity;
    }

    @Named("mapUserIdEntityToUser")
    default int mapUserIdEntityToUser(@NotNull UserEntity userEntity) {
        return userEntity.getId();
    }

    UserEntity toEntity(UserDTO userDTO);

    UserDTO toDto(UserEntity userEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "name", source = "fullname")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "userDetailsRoleDTO", source = "roleEntity")
    UserDetailsDTO toUserDetailsDTO(UserEntity userEntity);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "roleEntity", ignore = true)
    UserEntity toEntity(AdminVO adminVO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserDTO userDTO, @MappingTarget UserEntity userEntity);
}