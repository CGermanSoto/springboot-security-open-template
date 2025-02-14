package com.spacecodee.springbootsecurityopentemplate.mappers.security;

import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.CreateJwtTokenVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.UpdateJwtTokenVO;
import com.spacecodee.springbootsecurityopentemplate.enums.TokenStateEnum;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {
        IUserSecurityMapper.class
}, imports = {
        TokenStateEnum.class, Instant.class
})
public interface IJwtTokenSecurityMapper {

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

    @Named("getCurrentInstant")
    default Instant getCurrentInstant() {
        return Instant.now();
    }

    @Named("generateTokenId")
    default String generateTokenId(String username) {
        return String.format("%s_%d", username, Instant.now().toEpochMilli());
    }

    @Mapping(target = "jti", source = "jti")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "valid", source = "valid")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "userEntity.id", source = "userId")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "isRevoked", source = "isRevoked")
    @Mapping(target = "revokedAt", source = "revokedAt")
    @Mapping(target = "revokedReason", source = "revokedReason")
    @Mapping(target = "refreshCount", source = "refreshCount")
    @Mapping(target = "lastRefreshAt", source = "lastRefreshAt")
    @Mapping(target = "previousToken", source = "previousToken")
    JwtTokenEntity toEntity(CreateJwtTokenVO vo);

    @Mapping(target = "jti", source = "jti")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "valid", source = "valid")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "userEntity.id", source = "userId")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "isRevoked", source = "isRevoked")
    @Mapping(target = "revokedAt", source = "revokedAt")
    @Mapping(target = "revokedReason", source = "revokedReason")
    @Mapping(target = "refreshCount", source = "refreshCount")
    @Mapping(target = "lastRefreshAt", source = "lastRefreshAt")
    @Mapping(target = "previousToken", source = "previousToken")
    JwtTokenEntity toEntity(UpdateJwtTokenVO vo);

    @Mapping(target = "token", source = "token")
    @Mapping(target = "valid", constant = "true")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "expiryDate", source = "expireDate")
    @Mapping(target = "state", constant = "CREATED")
    @Mapping(target = "isRevoked", constant = "false")
    @Mapping(target = "refreshCount", constant = "0")
    CreateJwtTokenVO toCreateVO(String token, Instant expireDate, int userId);

    @Mapping(target = "token", source = "token")
    @Mapping(target = "valid", constant = "true")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "expiryDate", source = "expireDate")
    @Mapping(target = "state", constant = "ACTIVE")
    @Mapping(target = "isRevoked", constant = "false")
    UpdateJwtTokenVO toUpdateVO(String token, Instant expireDate, int userId);

    @Mapping(target = "token", source = "newToken")
    @Mapping(target = "previousToken", source = "existingToken.token")
    @Mapping(target = "valid", constant = "true")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "state", constant = "ACTIVE")
    @Mapping(target = "isRevoked", constant = "false")
    @Mapping(target = "lastAccessAt", expression = "java(getCurrentInstant())")
    @Mapping(target = "lastRefreshAt", expression = "java(getCurrentInstant())")
    @Mapping(target = "refreshCount", expression = "java(existingToken.getRefreshCount() + 1)")
    @Mapping(target = "lastOperation", constant = "Token refreshed during login")
    @Mapping(target = "userId", source = "existingToken.userEntity.id")
    UpdateJwtTokenVO toRefreshTokenVO(String newToken, Instant expiryDate, JwtTokenEntity existingToken);

    /**
     * Updates an existing JWT token entity with new values
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateJwtTokenEntity(@MappingTarget JwtTokenEntity target, JwtTokenEntity source);
}