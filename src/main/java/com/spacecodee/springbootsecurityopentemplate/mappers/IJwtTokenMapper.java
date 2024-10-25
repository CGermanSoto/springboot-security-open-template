package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.ticklyspace.data.dto.JwtTokenDTO;
import com.spacecodee.ticklyspace.persistence.entity.JwtTokenEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {IUserMapper.class})
public interface IJwtTokenMapper {
    JwtTokenEntity toEntity(JwtTokenDTO jwtTokenDTO);

    JwtTokenDTO toDto(JwtTokenEntity jwtTokenEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    JwtTokenEntity partialUpdate(JwtTokenDTO jwtTokenDTO, @MappingTarget JwtTokenEntity jwtTokenEntity);
}