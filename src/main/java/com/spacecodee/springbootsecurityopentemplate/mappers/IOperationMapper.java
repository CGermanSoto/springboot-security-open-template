package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.ticklyspace.data.dto.OperationEntityDto;
import com.spacecodee.ticklyspace.data.dto.user.details.UserDetailsOperationDTO;
import com.spacecodee.ticklyspace.persistence.entity.OperationEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {IModuleMapper.class})
public interface IOperationMapper {
    OperationEntity toEntity(OperationEntityDto operationEntityDto);

    OperationEntityDto toDTO(OperationEntity operationEntity);

    @Mapping(target = "moduleDTO", source = "moduleEntity")
    UserDetailsOperationDTO toUserDetailsOperationDTO(OperationEntity operationEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OperationEntity partialUpdate(OperationEntityDto operationEntityDto, @MappingTarget OperationEntity operationEntity);
}