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

    USER(List.of(
            RolePermissionEnum.SHOW_PROFILE,
            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN
    ));

    private List<RolePermissionEnum> permissions;
}
