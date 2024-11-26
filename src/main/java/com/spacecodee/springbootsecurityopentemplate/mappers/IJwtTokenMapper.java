package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.JwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.SecurityJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsJwtTokenDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.jwt.JwtTokeUVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.JwtTokenEntity;
import org.mapstruct.*;

import java.util.Date;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {IUserMapper.class})
public interface IJwtTokenMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "token", source = "token")
    @Mapping(target = "valid", constant = "true")
    @Mapping(target = "userEntity", source = "userDetailsId", qualifiedByName = "mapUserIdToUserEntity")
    @Mapping(target = "expiryDate", source = "expireDate")
    JwtTokeUVO toUVO(String token, Date expireDate, int userDetailsId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "token", source = "token")
    @Mapping(target = "isValid", constant = "true")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "userEntity", source = "userEntity")
    JwtTokenEntity voToEntity(JwtTokeUVO jwtTokeUVO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "isValid", constant = "true")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "userEntity", source = "userDetailsId", qualifiedByName = "mapUserIdToUserEntity")
    JwtTokenEntity dtoToEntity(SecurityJwtTokenDTO jwtTokeUVO);

    JwtTokenEntity toEntity(JwtTokenDTO jwtTokenDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "isValid", source = "isValid")
    JwtTokenDTO toDTO(JwtTokenEntity jwtTokenEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "valid", source = "isValid")
    @Mapping(target = "userDetailsDTO", source = "userEntity")
    UserDetailsJwtTokenDTO toUDDTO(JwtTokenEntity jwtTokenEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "valid", source = "isValid")
    @Mapping(target = "userDetailsId", source = "userEntity", qualifiedByName = "mapUserIdEntityToUser")
    SecurityJwtTokenDTO toSecurityJwtTokenDTO(JwtTokenEntity jwtTokenEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    JwtTokenEntity partialUpdate(JwtTokenDTO jwtTokenDTO, @MappingTarget JwtTokenEntity jwtTokenEntity);
}