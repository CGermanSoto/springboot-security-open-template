package com.spacecodee.springbootsecurityopentemplate.mappers.basic;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IRoleMapper.class})
public interface IDeveloperMapper {

}