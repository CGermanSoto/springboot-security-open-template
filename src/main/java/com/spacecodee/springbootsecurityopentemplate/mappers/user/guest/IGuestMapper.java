package com.spacecodee.springbootsecurityopentemplate.mappers.user.guest;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest.GuestDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest.GuestDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.CreateGuestVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest.UpdateGuestVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IGuestMapper {

    @Named("toDto")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    @Mapping(target = "status", source = "status")
    GuestDTO toDto(UserEntity entity);

    @Named("toDetailDto")
    @InheritConfiguration(name = "toDto")
    @Mapping(target = "roleId", source = "roleEntity.id")
    @Mapping(target = "roleName", source = "roleEntity.name")
    GuestDetailDTO toDetailDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    UserEntity toEntity(CreateGuestVO vo);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "profilePicturePath", source = "profilePicturePath")
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget UserEntity entity, UpdateGuestVO vo);

    @IterableMapping(qualifiedByName = "toDto")
    List<GuestDTO> toDtoList(List<UserEntity> entities);

    @IterableMapping(qualifiedByName = "toDetailDto")
    List<GuestDetailDTO> toDetailDtoList(List<UserEntity> entities);
}
