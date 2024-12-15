package com.spacecodee.springbootsecurityopentemplate.mappers.basic;

import com.spacecodee.springbootsecurityopentemplate.data.base.IJwtTokenFields;
import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.JwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.auth.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.jwt.JwtTokenUVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import org.mapstruct.*;

import java.util.Date;

/**
 * Mapper for JWT token-related conversions
 * Handles mappings between entities, DTOs and VOs for JWT tokens
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IUserMapper.class})
public interface IJwtTokenMapper {

    // Common mappings that can be reused
    @Named("mapCommonJwtFields")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "token", source = "token")
    @Mapping(target = "isValid", constant = "true")
    @Mapping(target = "expiryDate", source = "expiryDate")
    JwtTokenEntity mapCommonJwtFields(IJwtTokenFields source);

    // Create operations
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "token", source = "token")
    @Mapping(target = "valid", constant = "true")
    @Mapping(target = "userEntity", source = "userDetailsId", qualifiedByName = "mapUserIdToUserEntity")
    @Mapping(target = "expiryDate", source = "expireDate")
    JwtTokenUVO toUVO(String token, Date expireDate, int userDetailsId);

    // Entity conversions
    @InheritConfiguration(name = "mapCommonJwtFields")
    @Mapping(target = "userEntity", source = "userEntity")
    JwtTokenEntity voToEntity(JwtTokenUVO jwtTokenUVO);

    @InheritConfiguration(name = "mapCommonJwtFields")
    @Mapping(target = "userEntity", source = "userDetailsId", qualifiedByName = "mapUserIdToUserEntity")
    JwtTokenEntity dtoToEntity(SecurityJwtTokenDTO securityJwtTokenDTO);

    // DTO conversions
    @Mapping(target = "id", source = "id")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "isValid", source = "isValid")
    JwtTokenDTO toDTO(JwtTokenEntity jwtTokenEntity);

    // Security-specific conversions
    @Mapping(target = "id", source = "id")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "valid", source = "isValid")
    @Mapping(target = "userDetailsId", source = "userEntity", qualifiedByName = "mapUserIdEntityToUser")
    SecurityJwtTokenDTO toSecurityJwtTokenDTO(JwtTokenEntity jwtTokenEntity);

    /**
     * Updates an existing JWT token entity with new values
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateJwtTokenEntity(@MappingTarget JwtTokenEntity target, JwtTokenEntity source);
}