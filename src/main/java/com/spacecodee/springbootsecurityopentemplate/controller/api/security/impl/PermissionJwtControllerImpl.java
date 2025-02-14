package com.spacecodee.springbootsecurityopentemplate.controller.api.security.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.security.IPermissionJwtController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission.PermissionDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.security.UserSecurityDTO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.core.permission.IPermissionService;
import com.spacecodee.springbootsecurityopentemplate.service.security.user.IUserSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/security/permission-jwt")
public class PermissionJwtControllerImpl extends AbstractController implements IPermissionJwtController {

    private final IPermissionService permissionService;

    private final IUserSecurityService userSecurityService;

    public PermissionJwtControllerImpl(MessageParameterHandler messageParameterHandler, IPermissionService permissionService, IUserSecurityService userSecurityService) {
        super(messageParameterHandler);
        this.permissionService = permissionService;
        this.userSecurityService = userSecurityService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<PermissionDetailDTO>>> getCurrentUserPermissions() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (UserSecurityDTO) this.userSecurityService.loadUserByUsername(auth.getName());

        List<PermissionDetailDTO> permissions = this.permissionService.getAllPermissionDetailsByRoleId(
                userDetails.getRoleSecurityDTO().getId());

        return ResponseEntity.ok(super.createDataResponse(
                permissions,
                "permission.user.list.success",
                HttpStatus.OK,
                permissions.size()));
    }
}
