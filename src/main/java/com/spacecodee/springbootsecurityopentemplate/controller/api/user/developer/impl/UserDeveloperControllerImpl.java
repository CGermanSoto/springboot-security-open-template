// UserDeveloperControllerImpl.java
package com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer.IUserDeveloperController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperUVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
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
@RequestMapping("/user-developer")
public class UserDeveloperControllerImpl extends AbstractController implements IUserDeveloperController {
    private final IUserDeveloperService userDeveloperService;

    public UserDeveloperControllerImpl(MessageUtilComponent messageUtilComponent,
                                       MessageParameterHandler messageParameterHandler,
                                       IUserDeveloperService userDeveloperService) {
        super(messageUtilComponent, messageParameterHandler);
        this.userDeveloperService = userDeveloperService;
    }

    @Override
    public ResponseEntity<ApiResponsePojo> add(@Valid @RequestBody DeveloperAVO request, String locale) {
        this.userDeveloperService.add(request, locale);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createResponse("developer.added.success", locale,
                        HttpStatus.CREATED, request.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> update(String locale, int id, @Valid DeveloperUVO request) {
        this.userDeveloperService.update(id, request, locale);
        return ResponseEntity.ok(super.createResponse("developer.updated.success",
                locale, HttpStatus.OK, request.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> delete(String locale, int id) {
        var developer = this.userDeveloperService.findById(id, locale);
        this.userDeveloperService.delete(id, locale);
        return ResponseEntity.ok(super.createResponse("developer.deleted.success",
                locale, HttpStatus.OK, developer.username()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> findById(String locale, int id) {
        var developer = this.userDeveloperService.findById(id, locale);
        return ResponseEntity.ok(super.createDataResponse(developer,
                "developer.found.success", locale, HttpStatus.OK, developer.username()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<DeveloperDTO>>> findAll(String locale) {
        var developers = this.userDeveloperService.findAll(locale);
        return ResponseEntity.ok(super.createDataResponse(developers,
                "developer.list.success", locale, HttpStatus.OK, developers.size()));
    }
}