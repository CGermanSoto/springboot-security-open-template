package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.JwtTokenDTO;
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

    JwtTokenEntity voToEntity(JwtTokeUVO jwtTokeUVO);

    JwtTokenEntity toEntity(JwtTokenDTO jwtTokenDTO);

    JwtTokenDTO toDto(JwtTokenEntity jwtTokenEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    JwtTokenEntity partialUpdate(JwtTokenDTO jwtTokenDTO, @MappingTarget JwtTokenEntity jwtTokenEntity);
}