package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.UserDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserMapper {

    @Named("mapUserIdToUserEntity")
    default UserEntity mapUserIdToUserEntity(int userId) {
        var userEntity = new UserEntity();
        userEntity.setId(userId);
        return userEntity;
    }

    @Named("mapUserIdEntityToUser")
    default Integer mapUserIdEntityToUser(UserEntity userEntity) {
        return userEntity != null ? userEntity.getId() : null;
    }

    @Mapping(target = "roleDTO.id", source = "roleEntity.id")
    @Mapping(target = "roleDTO.name", source = "roleEntity.name")
    UserDTO toDto(UserEntity userEntity);

    @Mapping(target = "roleEntity", ignore = true)
    UserEntity toEntity(AdminVO adminVO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserDTO userDTO, @MappingTarget UserEntity userEntity);
}