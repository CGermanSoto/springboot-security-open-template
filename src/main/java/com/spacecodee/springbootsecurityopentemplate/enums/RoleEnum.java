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
            RolePermissionEnum.CREATE_ONE_SYSTEM_ADMIN,
            RolePermissionEnum.UPDATE_ONE_SYSTEM_ADMIN,
            RolePermissionEnum.UPDATE_STATUS_ONE_SYSTEM_ADMIN,
            RolePermissionEnum.DELETE_ONE_SYSTEM_ADMIN,
            RolePermissionEnum.FIND_ONE_SYSTEM_ADMIN,
            RolePermissionEnum.FIND_ONE_DETAIL_SYSTEM_ADMIN,
            RolePermissionEnum.FIND_ALL_PAGE_SYSTEM_ADMINS,

            RolePermissionEnum.CREATE_ONE_DEVELOPER,
            RolePermissionEnum.DELETE_ONE_DEVELOPER,
            RolePermissionEnum.UPDATE_ONE_DEVELOPER,
            RolePermissionEnum.UPDATE_STATUS_ONE_DEVELOPER,
            RolePermissionEnum.FIND_ONE_DEVELOPER,
            RolePermissionEnum.FIND_ONE_DETAIL_DEVELOPER,
            RolePermissionEnum.FIND_ALL_PAGE_DEVELOPERS,

            RolePermissionEnum.CREATE_ONE_MANAGER,
            RolePermissionEnum.DELETE_ONE_MANAGER,
            RolePermissionEnum.UPDATE_ONE_MANAGER,
            RolePermissionEnum.UPDATE_STATUS_ONE_MANAGER,
            RolePermissionEnum.FIND_ONE_MANAGER,
            RolePermissionEnum.FIND_ONE_DETAIL_MANAGER,
            RolePermissionEnum.FIND_ALL_PAGE_MANAGERS,

            RolePermissionEnum.CREATE_ONE_EDITOR,
            RolePermissionEnum.DELETE_ONE_EDITOR,
            RolePermissionEnum.UPDATE_ONE_EDITOR,
            RolePermissionEnum.UPDATE_STATUS_ONE_EDITOR,
            RolePermissionEnum.FIND_ONE_EDITOR,
            RolePermissionEnum.FIND_ONE_DETAIL_EDITOR,
            RolePermissionEnum.FIND_ALL_PAGE_EDITORS,

            RolePermissionEnum.CREATE_ONE_VIEWER,
            RolePermissionEnum.DELETE_ONE_VIEWER,
            RolePermissionEnum.UPDATE_ONE_VIEWER,
            RolePermissionEnum.UPDATE_STATUS_ONE_VIEWER,
            RolePermissionEnum.FIND_ONE_VIEWER,
            RolePermissionEnum.FIND_ONE_DETAIL_VIEWER,
            RolePermissionEnum.FIND_ALL_PAGE_VIEWER,

            RolePermissionEnum.CREATE_ONE_GUEST,
            RolePermissionEnum.DELETE_ONE_GUEST,
            RolePermissionEnum.UPDATE_ONE_GUEST,
            RolePermissionEnum.UPDATE_STATUS_ONE_GUEST,
            RolePermissionEnum.FIND_ONE_GUEST,
            RolePermissionEnum.FIND_ONE_DETAIL_GUEST,
            RolePermissionEnum.FIND_ALL_PAGE_GUEST,

            RolePermissionEnum.SHOW_USER_PROFILE,

            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN,

            RolePermissionEnum.FIND_PERMISSIONS_BY_CURRENT_USER,

            RolePermissionEnum.CREATE_ONE_MODULE,
            RolePermissionEnum.UPDATE_ONE_MODULE,
            RolePermissionEnum.DELETE_ONE_MODULE,
            RolePermissionEnum.FIND_ONE_MODULE,
            RolePermissionEnum.FIND_ALL_MODULE,
            RolePermissionEnum.FIND_ALL_PAGE_MODULE,

            RolePermissionEnum.CREATE_ONE_OPERATION,
            RolePermissionEnum.UPDATE_ONE_OPERATION,
            RolePermissionEnum.DELETE_ONE_OPERATION,
            RolePermissionEnum.FIND_ONE_OPERATION,
            RolePermissionEnum.FIND_ONE_DETAIL_OPERATION,
            RolePermissionEnum.FIND_ALL_PAGE_OPERATION,
            RolePermissionEnum.FIND_ALL_OPERATION_BY_MODULE,
            RolePermissionEnum.FIND_ALL_OPERATION,

            RolePermissionEnum.CREATE_ONE_ROLE,
            RolePermissionEnum.UPDATE_ONE_ROLE,
            RolePermissionEnum.DELETE_ONE_ROLE,
            RolePermissionEnum.FIND_ONE_ROLE,
            RolePermissionEnum.FIND_ONE_DETAIL_ROLE,
            RolePermissionEnum.FIND_ALL_PAGE_ROLE,
            RolePermissionEnum.FIND_ALL_ROLE,

            RolePermissionEnum.CREATE_ONE_PERMISSION,
            RolePermissionEnum.UPDATE_ONE_PERMISSION,
            RolePermissionEnum.DELETE_ONE_PERMISSION,
            RolePermissionEnum.FIND_ONE_PERMISSION,
            RolePermissionEnum.FIND_ONE_DETAIL_PERMISSION,
            RolePermissionEnum.FIND_ALL_PAGE_PERMISSION,
            RolePermissionEnum.FIND_ALL_PERMISSION,
            RolePermissionEnum.FIND_ALL_PERMISSION_BY_ROLE,

            RolePermissionEnum.GET_CACHE_STATS,

            RolePermissionEnum.GET_RATE_LIMIT_STATS,
            RolePermissionEnum.RESET_IP_RATE_LIMIT,
            RolePermissionEnum.CLEAN_UP_RATE_LIMIT
    )),

    SYSTEM_ADMIN(Arrays.asList(
            RolePermissionEnum.CREATE_ONE_SYSTEM_ADMIN,
            RolePermissionEnum.UPDATE_ONE_SYSTEM_ADMIN,
            RolePermissionEnum.UPDATE_STATUS_ONE_SYSTEM_ADMIN,
            RolePermissionEnum.DELETE_ONE_SYSTEM_ADMIN,
            RolePermissionEnum.FIND_ONE_SYSTEM_ADMIN,
            RolePermissionEnum.FIND_ONE_DETAIL_SYSTEM_ADMIN,
            RolePermissionEnum.FIND_ALL_PAGE_SYSTEM_ADMINS,

            RolePermissionEnum.CREATE_ONE_MANAGER,
            RolePermissionEnum.DELETE_ONE_MANAGER,
            RolePermissionEnum.UPDATE_ONE_MANAGER,
            RolePermissionEnum.UPDATE_STATUS_ONE_MANAGER,
            RolePermissionEnum.FIND_ONE_MANAGER,
            RolePermissionEnum.FIND_ONE_DETAIL_MANAGER,
            RolePermissionEnum.FIND_ALL_PAGE_MANAGERS,

            RolePermissionEnum.CREATE_ONE_EDITOR,
            RolePermissionEnum.DELETE_ONE_EDITOR,
            RolePermissionEnum.UPDATE_ONE_EDITOR,
            RolePermissionEnum.UPDATE_STATUS_ONE_EDITOR,
            RolePermissionEnum.FIND_ONE_EDITOR,
            RolePermissionEnum.FIND_ONE_DETAIL_EDITOR,
            RolePermissionEnum.FIND_ALL_PAGE_EDITORS,

            RolePermissionEnum.CREATE_ONE_VIEWER,
            RolePermissionEnum.DELETE_ONE_VIEWER,
            RolePermissionEnum.UPDATE_ONE_VIEWER,
            RolePermissionEnum.UPDATE_STATUS_ONE_VIEWER,
            RolePermissionEnum.FIND_ONE_VIEWER,
            RolePermissionEnum.FIND_ONE_DETAIL_VIEWER,
            RolePermissionEnum.FIND_ALL_PAGE_VIEWER,

            RolePermissionEnum.CREATE_ONE_GUEST,
            RolePermissionEnum.DELETE_ONE_GUEST,
            RolePermissionEnum.UPDATE_ONE_GUEST,
            RolePermissionEnum.UPDATE_STATUS_ONE_GUEST,
            RolePermissionEnum.FIND_ONE_GUEST,
            RolePermissionEnum.FIND_ONE_DETAIL_GUEST,
            RolePermissionEnum.FIND_ALL_PAGE_GUEST,

            RolePermissionEnum.SHOW_USER_PROFILE,

            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN,

            RolePermissionEnum.FIND_PERMISSIONS_BY_CURRENT_USER,

            RolePermissionEnum.GET_CACHE_STATS,

            RolePermissionEnum.GET_RATE_LIMIT_STATS,
            RolePermissionEnum.RESET_IP_RATE_LIMIT,
            RolePermissionEnum.CLEAN_UP_RATE_LIMIT
    )),

    MANAGER(Arrays.asList(
            RolePermissionEnum.FIND_ONE_MANAGER,
            RolePermissionEnum.FIND_ONE_DETAIL_MANAGER,
            RolePermissionEnum.FIND_ALL_PAGE_MANAGERS,

            RolePermissionEnum.CREATE_ONE_EDITOR,
            RolePermissionEnum.DELETE_ONE_EDITOR,
            RolePermissionEnum.UPDATE_ONE_EDITOR,
            RolePermissionEnum.UPDATE_STATUS_ONE_EDITOR,
            RolePermissionEnum.FIND_ONE_EDITOR,
            RolePermissionEnum.FIND_ONE_DETAIL_EDITOR,
            RolePermissionEnum.FIND_ALL_PAGE_EDITORS,

            RolePermissionEnum.CREATE_ONE_VIEWER,
            RolePermissionEnum.DELETE_ONE_VIEWER,
            RolePermissionEnum.UPDATE_ONE_VIEWER,
            RolePermissionEnum.UPDATE_STATUS_ONE_VIEWER,
            RolePermissionEnum.FIND_ONE_VIEWER,
            RolePermissionEnum.FIND_ONE_DETAIL_VIEWER,
            RolePermissionEnum.FIND_ALL_PAGE_VIEWER,

            RolePermissionEnum.CREATE_ONE_GUEST,
            RolePermissionEnum.DELETE_ONE_GUEST,
            RolePermissionEnum.UPDATE_ONE_GUEST,
            RolePermissionEnum.UPDATE_STATUS_ONE_GUEST,
            RolePermissionEnum.FIND_ONE_GUEST,
            RolePermissionEnum.FIND_ONE_DETAIL_GUEST,
            RolePermissionEnum.FIND_ALL_PAGE_GUEST,

            RolePermissionEnum.SHOW_USER_PROFILE,

            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN,

            RolePermissionEnum.FIND_PERMISSIONS_BY_CURRENT_USER
    )),

    EDITOR(List.of(
            RolePermissionEnum.FIND_ONE_EDITOR,
            RolePermissionEnum.FIND_ONE_DETAIL_EDITOR,
            RolePermissionEnum.FIND_ALL_PAGE_EDITORS,

            RolePermissionEnum.CREATE_ONE_VIEWER,
            RolePermissionEnum.DELETE_ONE_VIEWER,
            RolePermissionEnum.UPDATE_ONE_VIEWER,
            RolePermissionEnum.UPDATE_STATUS_ONE_VIEWER,
            RolePermissionEnum.FIND_ONE_VIEWER,
            RolePermissionEnum.FIND_ONE_DETAIL_VIEWER,
            RolePermissionEnum.FIND_ALL_PAGE_VIEWER,

            RolePermissionEnum.CREATE_ONE_GUEST,
            RolePermissionEnum.DELETE_ONE_GUEST,
            RolePermissionEnum.UPDATE_ONE_GUEST,
            RolePermissionEnum.UPDATE_STATUS_ONE_GUEST,
            RolePermissionEnum.FIND_ONE_GUEST,
            RolePermissionEnum.FIND_ONE_DETAIL_GUEST,
            RolePermissionEnum.FIND_ALL_PAGE_GUEST,

            RolePermissionEnum.SHOW_USER_PROFILE,

            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN,

            RolePermissionEnum.FIND_PERMISSIONS_BY_CURRENT_USER
    )),

    VIEWER(List.of(
            RolePermissionEnum.FIND_ONE_VIEWER,
            RolePermissionEnum.FIND_ONE_DETAIL_VIEWER,
            RolePermissionEnum.FIND_ALL_PAGE_VIEWER,

            RolePermissionEnum.FIND_ONE_GUEST,
            RolePermissionEnum.FIND_ONE_DETAIL_GUEST,
            RolePermissionEnum.FIND_ALL_PAGE_GUEST,

            RolePermissionEnum.SHOW_USER_PROFILE,

            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN,

            RolePermissionEnum.FIND_PERMISSIONS_BY_CURRENT_USER
    )),

    GUEST(List.of(
            RolePermissionEnum.FIND_ONE_VIEWER,
            RolePermissionEnum.FIND_ONE_DETAIL_VIEWER,
            RolePermissionEnum.FIND_ALL_PAGE_VIEWER,

            RolePermissionEnum.FIND_ONE_GUEST,
            RolePermissionEnum.FIND_ONE_DETAIL_GUEST,
            RolePermissionEnum.FIND_ALL_PAGE_GUEST,

            RolePermissionEnum.SHOW_USER_PROFILE,

            RolePermissionEnum.VALIDATE_TOKEN,
            RolePermissionEnum.LOGOUT,
            RolePermissionEnum.REFRESH_TOKEN,

            RolePermissionEnum.FIND_PERMISSIONS_BY_CURRENT_USER
    ));

    private List<RolePermissionEnum> permissions;
}
