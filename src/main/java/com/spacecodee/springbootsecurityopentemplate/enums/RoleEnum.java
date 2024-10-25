package com.spacecodee.springbootsecurityopentemplate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum RoleEnum {

    OTI_ADMIN, DEVELOPER(Arrays.asList(
            RolePermissionEnum.READ_ALL_SONGS,
            RolePermissionEnum.READ_ONE_SONG,
            RolePermissionEnum.CREATE_ONE_SONG,
            RolePermissionEnum.UPDATE_ONE_SONG,
            RolePermissionEnum.DISABLE_ONE_SONG,

            RolePermissionEnum.READ_ALL_GENRES,
            RolePermissionEnum.READ_ONE_GENRE,
            RolePermissionEnum.CREATE_ONE_GENRE,
            RolePermissionEnum.UPDATE_ONE_GENRE,
            RolePermissionEnum.DISABLE_ONE_GENRE,

            RolePermissionEnum.DELETE_NO_ACTIVE_TOKENS,
            RolePermissionEnum.CREATE_ONE_USER_SYSTEM,

            RolePermissionEnum.READ_MY_PROFILE
    )),

    TECHNICIAN(Arrays.asList(
            RolePermissionEnum.READ_ALL_SONGS,
            RolePermissionEnum.READ_ONE_SONG,
            RolePermissionEnum.UPDATE_ONE_SONG,

            RolePermissionEnum.READ_ALL_GENRES,
            RolePermissionEnum.READ_ONE_GENRE,
            RolePermissionEnum.UPDATE_ONE_GENRE,

            RolePermissionEnum.DELETE_NO_ACTIVE_TOKENS,

            RolePermissionEnum.READ_MY_PROFILE
    )),

    USER(List.of(
            RolePermissionEnum.READ_MY_PROFILE
    ));

    private List<RolePermissionEnum> permissions;
}
