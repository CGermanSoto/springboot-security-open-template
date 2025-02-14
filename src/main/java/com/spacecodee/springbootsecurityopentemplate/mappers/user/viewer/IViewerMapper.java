package com.spacecodee.springbootsecurityopentemplate.mappers.user.viewer;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer.ViewerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer.ViewerDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.CreateViewerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.UpdateViewerVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IViewerMapper {

    @Named("toDto")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    @Mapping(target = "status", source = "status")
    ViewerDTO toDto(UserEntity entity);

    @Named("toDetailDto")
    @InheritConfiguration(name = "toDto")
    @Mapping(target = "roleId", source = "roleEntity.id")
    @Mapping(target = "roleName", source = "roleEntity.name")
    ViewerDetailDTO toDetailDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    UserEntity toEntity(CreateViewerVO vo);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget UserEntity entity, UpdateViewerVO vo);

    @IterableMapping(qualifiedByName = "toDto")
    List<ViewerDTO> toDtoList(List<UserEntity> entities);

    @IterableMapping(qualifiedByName = "toDetailDto")
    List<ViewerDetailDTO> toDetailDtoList(List<UserEntity> entities);
}
