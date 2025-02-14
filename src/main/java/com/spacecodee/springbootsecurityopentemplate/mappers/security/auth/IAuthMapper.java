package com.spacecodee.springbootsecurityopentemplate.mappers.security.auth;

import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.AuthResponseDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.CreateJwtTokenVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.UpdateJwtTokenVO;
import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.mappers.security.IRoleSecurityMapper;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = {IRoleSecurityMapper.class}, imports = {
        TokenStateEnum.class, Instant.class})
public interface IAuthMapper {

    @Named("getCurrentInstant")
    default Instant getCurrentInstant() {
        return Instant.now();
    }

    @Named("generateTokenId")
    default String generateTokenId(String username) {
        return String.format("%s_%d", username, Instant.now().toEpochMilli());
    }

    @Named("toCreateJwtTokenVO")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "valid", constant = "true")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "userId", source = "userEntity.id")
    @Mapping(target = "state", expression = "java(TokenStateEnum.ACTIVE.name())")
    @Mapping(target = "isRevoked", constant = "false")
    @Mapping(target = "usageCount", constant = "0")
    @Mapping(target = "lastAccessAt", expression = "java(getCurrentInstant())")
    @Mapping(target = "jti", expression = "java(generateTokenId(userEntity.getUsername()))")
    CreateJwtTokenVO toCreateJwtTokenVO(String token, UserEntity userEntity, Instant expiryDate);

    @Named("toUpdateJwtTokenVO")
    @Mapping(target = "id", source = "tokenEntity.id")
    @Mapping(target = "token", source = "newToken")
    @Mapping(target = "valid", constant = "true")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "userId", source = "tokenEntity.userEntity.id")
    @Mapping(target = "state", expression = "java(TokenStateEnum.ACTIVE.name())")
    @Mapping(target = "previousToken", source = "tokenEntity.token")
    @Mapping(target = "lastAccessAt", expression = "java(getCurrentInstant())")
    UpdateJwtTokenVO toUpdateJwtTokenVO(JwtTokenEntity tokenEntity, String newToken, Instant expiryDate);

    @Mapping(target = "token", source = "jwtTokenEntity.token")
    @Mapping(target = "expiresAt", source = "jwtTokenEntity.expiryDate")
    @Mapping(target = "username", source = "userEntity.username")
    @Mapping(target = "role", source = "userEntity.roleEntity.name")
    AuthResponseDTO toAuthResponseDTO(JwtTokenEntity jwtTokenEntity, UserEntity userEntity);

    @Named("toAuthUserSecurityDTO")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "roleSecurityDTO", source = "roleEntity")
    UserSecurityDTO toAuthUserSecurityDTO(UserEntity userEntity);
}