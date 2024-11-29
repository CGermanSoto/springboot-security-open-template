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

    DEVELOPER(Arrays.asList(
            RolePermissionEnum.REGISTER_ONE_ADMIN,
            RolePermissionEnum.UPDATE_ONE_ADMIN,
            RolePermissionEnum.DELETE_ONE_ADMIN,
            RolePermissionEnum.FIND_ONE_ADMIN,
            RolePermissionEnum.FIND_ALL_ADMINS,

            RolePermissionEnum.REGISTER_ONE_DEVELOPER,
            RolePermissionEnum.DELETE_ONE_DEVELOPER,
            RolePermissionEnum.UPDATE_ONE_DEVELOPER,
            RolePermissionEnum.FIND_ONE_DEVELOPER,
            RolePermissionEnum.FIND_ALL_DEVELOPERS,

            RolePermissionEnum.SHOW_PROFILE,
            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN,

            RolePermissionEnum.MODULE,
            RolePermissionEnum.OPERATION,
            RolePermissionEnum.PERMISSION,
            RolePermissionEnum.PERMISSION_DELETE,
            RolePermissionEnum.OPERATION_DELETE,
            RolePermissionEnum.MODULE_DELETE
    )),

    OTI_ADMIN(Arrays.asList(
            RolePermissionEnum.REGISTER_ONE_ADMIN,
            RolePermissionEnum.UPDATE_ONE_ADMIN,
            RolePermissionEnum.DELETE_ONE_ADMIN,
            RolePermissionEnum.FIND_ONE_ADMIN,
            RolePermissionEnum.FIND_ALL_ADMINS,

            RolePermissionEnum.REGISTER_ONE_DEVELOPER,

            RolePermissionEnum.SHOW_PROFILE,
            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN
    )),

    TECHNICIAN(Arrays.asList(
            RolePermissionEnum.REGISTER_ONE_ADMIN,

            RolePermissionEnum.SHOW_PROFILE,
            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN
    )),

    CUSTOMER(List.of(
            RolePermissionEnum.SHOW_PROFILE,
            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN
    ));

    private List<RolePermissionEnum> permissions;
}
