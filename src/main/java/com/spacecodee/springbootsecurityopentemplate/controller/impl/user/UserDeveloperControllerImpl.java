// UserDeveloperControllerImpl.java
package com.spacecodee.springbootsecurityopentemplate.controller.impl.user;

import com.spacecodee.springbootsecurityopentemplate.controller.user.IUserDeveloperController;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.DeveloperUVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.user.IUserDeveloperService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-developer")
@AllArgsConstructor
public class UserDeveloperControllerImpl implements IUserDeveloperController {

    private final IUserDeveloperService userDeveloperService;
    private final MessageUtilComponent messageUtilComponent;

    @Override
    public ResponseEntity<ApiResponsePojo> add(DeveloperAVO request, String locale) {
        var apiResponse = new ApiResponsePojo();
        this.userDeveloperService.add(request, locale);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("developer.added.success", locale));
        apiResponse.setHttpStatus(HttpStatus.CREATED);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponsePojo> update(String locale, int id, DeveloperUVO request) {
        var apiResponse = new ApiResponsePojo();
        this.userDeveloperService.update(id, request, locale);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("developer.updated.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponsePojo> delete(String locale, int id) {
        var apiResponse = new ApiResponsePojo();
        this.userDeveloperService.delete(id, locale);
        apiResponse.setMessage(this.messageUtilComponent.getMessage("developer.deleted.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> findById(String locale, int id) {
        var apiResponse = new ApiResponseDataPojo<DeveloperDTO>();
        apiResponse.setData(this.userDeveloperService.findById(id, locale));
        apiResponse.setMessage(this.messageUtilComponent.getMessage("developer.found.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<DeveloperDTO>>> findAll(String locale) {
        var apiResponse = new ApiResponseDataPojo<List<DeveloperDTO>>();
        apiResponse.setData(this.userDeveloperService.findAll(locale));
        apiResponse.setMessage(this.messageUtilComponent.getMessage("developer.list.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return ResponseEntity.ok(apiResponse);
    }
}