package com.spacecodee.springbootsecurityopentemplate.enums;

public enum RolePermissionEnum {

    // Admin
    REGISTER_ONE_ADMIN,
    UPDATE_ONE_ADMIN,
    DELETE_ONE_ADMIN,

    // Developer
    REGISTER_ONE_DEVELOPER,
    DELETE_ONE_DEVELOPER,
    UPDATE_ONE_DEVELOPER,
    FIND_ONE_DEVELOPER,
    FIND_ALL_DEVELOPERS,

    // System
    SHOW_PROFILE,
    VALIDATE_TOKEN,
    LOGOUT,
    REFRESH_TOKEN,

    // Operations only for Developer
    MODULE,
    OPERATION,
    PERMISSION,
    PERMISSION_DELETE,
    OPERATION_DELETE,
    MODULE_DELETE,
}
