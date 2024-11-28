package com.spacecodee.springbootsecurityopentemplate.controller.api.user.technician.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.technician.IUserTechnicianController;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.TechnicianDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.TechnicianAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.TechnicianUVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.technician.IUserTechnicianService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user-technician")
@AllArgsConstructor
public class UserTechnicianControllerImpl implements IUserTechnicianController {

    private final IUserTechnicianService userTechnicianService;
    private final MessageUtilComponent messageUtilComponent;

    @Override
    public ResponseEntity<ApiResponsePojo> add(TechnicianAVO request, String locale) {
        var apiResponse = new ApiResponsePojo();
        this.userTechnicianService.add(request, locale);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("technician.added.success", locale));
        apiResponse.setHttpStatus(HttpStatus.CREATED);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponsePojo> update(String locale, int id, TechnicianUVO request) {
        var apiResponse = new ApiResponsePojo();
        this.userTechnicianService.update(id, request, locale);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("technician.updated.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponsePojo> delete(String locale, int id) {
        var apiResponse = new ApiResponsePojo();
        this.userTechnicianService.delete(id, locale);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("technician.deleted.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<TechnicianDTO>> findById(String locale, int id) {
        var apiResponse = new ApiResponseDataPojo<TechnicianDTO>();
        apiResponse.setData(this.userTechnicianService.findById(id, locale));
        apiResponse.setMessage(this.messageUtilComponent.getMessage("technician.found.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<TechnicianDTO>>> findAll(String locale) {
        var apiResponse = new ApiResponseDataPojo<List<TechnicianDTO>>();
        apiResponse.setData(this.userTechnicianService.findAll(locale));
        apiResponse.setMessage(this.messageUtilComponent.getMessage("technician.list.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }
}