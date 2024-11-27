// IOperationDetailsMapper.java
package com.spacecodee.springbootsecurityopentemplate.mappers.details;

import com.spacecodee.springbootsecurityopentemplate.data.dto.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.details.UserDetailsOperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.operation.OperationVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.OperationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IModuleDetailsMapper.class})
public interface IOperationDetailsMapper {

    @Mapping(target = "moduleDTO", source = "moduleEntity")
    UserDetailsOperationDTO toUserDetailsOperationDTO(OperationEntity operationEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "moduleEntity", source = "moduleId", qualifiedByName = "mapModuleIdToModuleEntity")
    OperationEntity voToEntity(OperationVO operationVO);

    @Mapping(target = "moduleId", source = "moduleEntity", qualifiedByName = "mapModuleEntityToModuleId")
    OperationDTO toDTO(OperationEntity operationEntity);
}