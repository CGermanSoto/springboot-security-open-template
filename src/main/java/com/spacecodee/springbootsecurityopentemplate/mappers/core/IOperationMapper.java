package com.spacecodee.springbootsecurityopentemplate.mappers.core;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation.OperationDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.CreateOperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.UpdateOperationVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.OperationEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = IModuleMapper.class)
public interface IOperationMapper {

    @Mapping(target = "moduleId", source = "moduleEntity.id")
    @Mapping(target = "moduleName", source = "moduleEntity.name")
    OperationDTO toDTO(OperationEntity entity);

    @Mapping(target = "moduleId", source = "moduleEntity.id")
    @Mapping(target = "moduleName", source = "moduleEntity.name")
    @Mapping(target = "moduleBasePath", source = "moduleEntity.basePath")
    OperationDetailDTO toDetailDTO(OperationEntity entity);

    List<OperationDTO> toDTOList(List<OperationEntity> entities);

    List<OperationDetailDTO> toDetailDTOList(List<OperationEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "moduleEntity", source = "moduleId", qualifiedByName = "moduleIdToEntity")
        //@Mapping(target = "permitAll", expression = "java(Boolean.parseBoolean(vo.getPermitAll()))")
    OperationEntity toEntity(CreateOperationVO vo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "moduleEntity", source = "moduleId", qualifiedByName = "moduleIdToEntity")
        //@Mapping(target = "permitAll", expression = "java(Boolean.parseBoolean(vo.getPermitAll()))")
    OperationEntity toEntity(UpdateOperationVO vo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "moduleEntity", source = "moduleId", qualifiedByName = "moduleIdToEntity")
        //@Mapping(target = "permitAll", expression = "java(Boolean.parseBoolean(vo.getPermitAll()))")
    void updateEntity(@MappingTarget OperationEntity entity, UpdateOperationVO vo);

    @Named("operationIdToEntity")
    default OperationEntity operationIdToEntity(Integer operationId) {
        if (operationId == null)
            return null;
        return new OperationEntity().setId(operationId);
    }
}