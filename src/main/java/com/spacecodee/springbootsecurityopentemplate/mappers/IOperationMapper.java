package com.spacecodee.springbootsecurityopentemplate.mappers;

import com.spacecodee.springbootsecurityopentemplate.data.dto.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsOperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.operation.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.OperationEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {IModuleMapper.class})
public interface IOperationMapper {
    OperationEntity toEntity(OperationDTO operationDTO);

    OperationDTO toDTO(OperationEntity operationEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "moduleDTO", source = "moduleEntity")
    UserDetailsOperationDTO toUserDetailsOperationDTO(OperationEntity operationEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OperationEntity partialUpdate(OperationDTO operationDTO, @MappingTarget OperationEntity operationEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "httpMethod", source = "httpMethod")
    @Mapping(target = "tag", source = "tag")
    @Mapping(target = "path", source = "path")
    @Mapping(target = "permitAll", source = "permitAll")
    @Mapping(target = "moduleEntity", source = "moduleId", qualifiedByName = "mapModuleIdToModuleEntity")
    OperationEntity voToEntity(OperationVO operationVO);
}