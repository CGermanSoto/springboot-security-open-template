package com.spacecodee.springbootsecurityopentemplate.enums;

public enum RolePermissionEnum {

    // General
    SYSTEM_CRUD_OPERATIONS, //TODO: Add this to all roles that can perform CRUD operations

    // Admin
    REGISTER_ONE_ADMIN,
    UPDATE_ONE_ADMIN,
    DELETE_ONE_ADMIN,
    FIND_ONE_ADMIN,
    FIND_ALL_ADMINS,

    // Developer
    REGISTER_ONE_DEVELOPER,
    DELETE_ONE_DEVELOPER,
    UPDATE_ONE_DEVELOPER,
    FIND_ONE_DEVELOPER,
    FIND_ALL_DEVELOPERS,

    // Technician
    REGISTER_ONE_TECHNICIAN,
    DELETE_ONE_TECHNICIAN,
    UPDATE_ONE_TECHNICIAN,
    FIND_ONE_TECHNICIAN,
    FIND_ALL_TECHNICIANS,

    // Customer
    REGISTER_ONE_CUSTOMER,
    DELETE_ONE_CUSTOMER,
    UPDATE_ONE_CUSTOMER,
    FIND_ONE_CUSTOMER,
    FIND_ALL_CUSTOMERS,

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
