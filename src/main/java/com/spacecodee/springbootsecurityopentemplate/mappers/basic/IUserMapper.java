package com.spacecodee.springbootsecurityopentemplate.mappers.basic;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

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
}