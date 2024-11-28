// UserDeveloperControllerImpl.java
package com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer.IUserDeveloperController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperUVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.developer.IUserDeveloperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
public class UserDeveloperControllerImpl extends AbstractController implements IUserDeveloperController {
    private final IUserDeveloperService userDeveloperService;

    public UserDeveloperControllerImpl(MessageUtilComponent messageUtilComponent,
                                       IUserDeveloperService userDeveloperService) {
        super(messageUtilComponent);
        this.userDeveloperService = userDeveloperService;
    }

    @Override
    public ResponseEntity<ApiResponsePojo> add(@Valid @RequestBody DeveloperAVO request, String locale) {
        this.userDeveloperService.add(request, locale);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createResponse("developer.added.success", locale, HttpStatus.CREATED));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> update(String locale, int id, @Valid DeveloperUVO request) {
        this.userDeveloperService.update(id, request, locale);
        return ResponseEntity.ok(super.createResponse("developer.updated.success", locale, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> delete(String locale, int id) {
        this.userDeveloperService.delete(id, locale);
        return ResponseEntity.ok(super.createResponse("developer.deleted.success", locale, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> findById(String locale, int id) {
        return ResponseEntity.ok(super.createDataResponse(
                this.userDeveloperService.findById(id, locale),
                "developer.found.success",
                locale,
                HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<DeveloperDTO>>> findAll(String locale) {
        return ResponseEntity.ok(super.createDataResponse(
                this.userDeveloperService.findAll(locale),
                "developer.list.success",
                locale,
                HttpStatus.OK));
    }
}