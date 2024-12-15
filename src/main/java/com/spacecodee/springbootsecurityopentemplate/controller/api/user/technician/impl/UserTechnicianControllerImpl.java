package com.spacecodee.springbootsecurityopentemplate.controller.api.user.technician.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.technician.IUserTechnicianController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.TechnicianDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician.TechnicianUVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
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
                                        MessageParameterHandler messageParameterHandler,
                                        IUserTechnicianService userTechnicianService) {
        super(messageUtilComponent, messageParameterHandler);
        this.userTechnicianService = userTechnicianService;
    }

    @Override
    public ResponseEntity<ApiResponsePojo> add(TechnicianAVO request, String locale) {
        this.userTechnicianService.add(request, locale);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createResponse("technician.added.success", locale,
                        HttpStatus.CREATED, request.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> update(String locale, int id, TechnicianUVO request) {
        this.userTechnicianService.update(id, request, locale);
        return ResponseEntity.ok(super.createResponse("technician.updated.success",
                locale, HttpStatus.OK, request.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> delete(String locale, int id) {
        var technician = this.userTechnicianService.findById(id, locale);
        this.userTechnicianService.delete(id, locale);
        return ResponseEntity.ok(super.createResponse("technician.deleted.success",
                locale, HttpStatus.OK, technician.username()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<TechnicianDTO>> findById(String locale, int id) {
        var technician = this.userTechnicianService.findById(id, locale);
        return ResponseEntity.ok(super.createDataResponse(technician,
                "technician.found.success", locale, HttpStatus.OK, technician.username()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<TechnicianDTO>>> findAll(String locale) {
        var technicians = this.userTechnicianService.findAll(locale);
        return ResponseEntity.ok(super.createDataResponse(technicians,
                "technician.list.success", locale, HttpStatus.OK, technicians.size()));
    }
}