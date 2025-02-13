package com.spacecodee.springbootsecurityopentemplate.mappers.user.manager;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager.ManagerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager.ManagerDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.CreateManagerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager.UpdateManagerVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IManagerMapper {

    @Named("toDto")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    @Mapping(target = "status", source = "status")
    ManagerDTO toDto(UserEntity entity);

    @Named("toDetailDto")
    @InheritConfiguration(name = "toDto")
    @Mapping(target = "roleId", source = "roleEntity.id")
    @Mapping(target = "roleName", source = "roleEntity.name")
    ManagerDetailDTO toDetailDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    UserEntity toEntity(CreateManagerVO vo);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget UserEntity entity, UpdateManagerVO vo);

    @IterableMapping(qualifiedByName = "toDto")
    List<ManagerDTO> toDtoList(List<UserEntity> entities);

    @IterableMapping(qualifiedByName = "toDetailDto")
    List<ManagerDetailDTO> toDetailDtoList(List<UserEntity> entities);
}
