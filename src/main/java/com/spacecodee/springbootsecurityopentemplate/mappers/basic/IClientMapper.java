package com.spacecodee.springbootsecurityopentemplate.mappers.basic;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.CustomerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerUVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IRoleMapper.class })
public interface IClientMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleEntity", ignore = true)
    UserEntity voToEntity(CustomerAVO customerAVO);

    @Mapping(target = "roleDTO", source = "roleEntity")
    CustomerDTO toDto(UserEntity userEntity);

    List<CustomerDTO> toDtoList(List<UserEntity> userEntities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity updateEntity(@MappingTarget UserEntity userEntity, CustomerUVO customerUVO);
}