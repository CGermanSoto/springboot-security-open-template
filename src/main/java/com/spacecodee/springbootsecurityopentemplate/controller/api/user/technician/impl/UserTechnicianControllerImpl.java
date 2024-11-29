package com.spacecodee.springbootsecurityopentemplate.controller.api.user.technician.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.technician.IUserTechnicianController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.TechnicianDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianUVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.technician.IUserTechnicianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-technician")
public class UserTechnicianControllerImpl extends AbstractController implements IUserTechnicianController {
    private final IUserTechnicianService userTechnicianService;

    public UserTechnicianControllerImpl(MessageUtilComponent messageUtilComponent,
                                        IUserTechnicianService userTechnicianService) {
        super(messageUtilComponent);
        this.userTechnicianService = userTechnicianService;
    }

    @Override
    public ResponseEntity<ApiResponsePojo> add(TechnicianAVO request, String locale) {
        this.userTechnicianService.add(request, locale);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createResponse("technician.added.success", locale, HttpStatus.CREATED));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> update(String locale, int id, TechnicianUVO request) {
        this.userTechnicianService.update(id, request, locale);
        return ResponseEntity.ok(super.createResponse("technician.updated.success", locale, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> delete(String locale, int id) {
        this.userTechnicianService.delete(id, locale);
        return ResponseEntity.ok(super.createResponse("technician.deleted.success", locale, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<TechnicianDTO>> findById(String locale, int id) {
        return ResponseEntity.ok(super.createDataResponse(
                this.userTechnicianService.findById(id, locale),
                "technician.found.success",
                locale,
                HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<TechnicianDTO>>> findAll(String locale) {
        return ResponseEntity.ok(super.createDataResponse(
                this.userTechnicianService.findAll(locale),
                "technician.list.success",
                locale,
                HttpStatus.OK));
    }
}