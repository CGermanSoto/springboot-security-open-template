package com.spacecodee.springbootsecurityopentemplate.mappers.user.developer;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer.DeveloperDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.CreateDeveloperVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.UpdateDeveloperVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IDeveloperMapper {

    @Named("toDto")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    @Mapping(target = "status", source = "status")
    DeveloperDTO toDto(UserEntity entity);

    @Named("toDetailDto")
    @InheritConfiguration(name = "toDto")
    @Mapping(target = "roleId", source = "roleEntity.id")
    @Mapping(target = "roleName", source = "roleEntity.name")
    DeveloperDetailDTO toDetailDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    UserEntity toEntity(CreateDeveloperVO vo);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget UserEntity entity, UpdateDeveloperVO vo);

    @IterableMapping(qualifiedByName = "toDto")
    List<DeveloperDTO> toDtoList(List<UserEntity> entities);

    @IterableMapping(qualifiedByName = "toDetailDto")
    List<DeveloperDetailDTO> toDetailDtoList(List<UserEntity> entities);
}
