package com.spacecodee.springbootsecurityopentemplate.mappers.user.editor;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor.EditorDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor.EditorDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.CreateEditorVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.UpdateEditorVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IEditorMapper {

    @Named("toDto")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    @Mapping(target = "status", source = "status")
    EditorDTO toDto(UserEntity entity);

    @Named("toDetailDto")
    @InheritConfiguration(name = "toDto")
    @Mapping(target = "roleId", source = "roleEntity.id")
    @Mapping(target = "roleName", source = "roleEntity.name")
    EditorDetailDTO toDetailDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    UserEntity toEntity(CreateEditorVO vo);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget UserEntity entity, UpdateEditorVO vo);

    @IterableMapping(qualifiedByName = "toDto")
    List<EditorDTO> toDtoList(List<UserEntity> entities);

    @IterableMapping(qualifiedByName = "toDetailDto")
    List<EditorDetailDTO> toDetailDtoList(List<UserEntity> entities);
}
