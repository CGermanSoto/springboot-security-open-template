package com.spacecodee.springbootsecurityopentemplate.controller.api.user.admin.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.admin.IUserAdminController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.AdminDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminUVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.admin.IUserAdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-admin")
public class UserAdminControllerImpl extends AbstractController implements IUserAdminController {
    private final IUserAdminService userAdminService;

    public UserAdminControllerImpl(MessageUtilComponent messageUtilComponent,
                                   MessageParameterHandler messageParameterHandler,
                                   IUserAdminService userAdminService) {
        super(messageUtilComponent, messageParameterHandler);
        this.userAdminService = userAdminService;
    }

    @Override
    public ResponseEntity<ApiResponsePojo> add(AdminAVO adminAVO, String locale) {
        this.userAdminService.add(adminAVO, locale);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createResponse("admin.added.success", locale,
                        HttpStatus.CREATED, adminAVO.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> update(String locale, int id, @Valid AdminUVO adminUVO) {
        this.userAdminService.update(id, adminUVO, locale);
        return ResponseEntity.ok(super.createResponse("admin.updated.success",
                locale, HttpStatus.OK, adminUVO.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> delete(String locale, int id) {
        var admin = this.userAdminService.findById(id, locale);
        this.userAdminService.delete(id, locale);
        return ResponseEntity.ok(super.createResponse("admin.deleted.success",
                locale, HttpStatus.OK, admin.username()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AdminDTO>> findById(String locale, int id) {
        var admin = this.userAdminService.findById(id, locale);
        return ResponseEntity.ok(super.createDataResponse(admin,
                "admin.found.success", locale, HttpStatus.OK, admin.username()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<AdminDTO>>> findAll(String locale) {
        var admins = this.userAdminService.findAll(locale);
        return ResponseEntity.ok(super.createDataResponse(admins,
                "admin.list.success", locale, HttpStatus.OK, admins.size()));
    }
}
