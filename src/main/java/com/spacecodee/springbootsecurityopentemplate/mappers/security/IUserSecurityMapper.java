package com.spacecodee.springbootsecurityopentemplate.mappers.security;

import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {
        IRoleSecurityMapper.class})
public interface IUserSecurityMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roleSecurityDTO", source = "roleEntity")
    UserSecurityDTO toUserSecurityDTO(UserEntity userEntity);

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