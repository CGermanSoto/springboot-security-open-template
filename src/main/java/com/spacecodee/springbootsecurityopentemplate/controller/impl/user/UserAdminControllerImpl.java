package com.spacecodee.springbootsecurityopentemplate.controller.impl.user;

import com.spacecodee.springbootsecurityopentemplate.controller.user.IUserAdminController;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.AdminUVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.admin.IUserAdminService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/user-admin")
public class UserAdminControllerImpl implements IUserAdminController {

    private final IUserAdminService userAdminService;
    private final MessageUtilComponent messageUtilComponent;

    @Override
    public ResponseEntity<ApiResponsePojo> add(AdminAVO adminAVO, String locale) {
        var apiResponse = new ApiResponsePojo();
        this.userAdminService.add(adminAVO, locale);

        apiResponse.setMessage(this.messageUtilComponent.getMessage("admin.added.success", locale));
        apiResponse.setHttpStatus(HttpStatus.CREATED);

        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> update(String locale, int id,
                                                  @Valid AdminUVO adminAVO) {
        var apiResponse = new ApiResponsePojo();
        this.userAdminService.update(id, adminAVO, locale);

        apiResponse.setMessage(this.messageUtilComponent.getMessage("admin.updated.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> delete(String locale, int id) {
        var apiResponse = new ApiResponsePojo();
        this.userAdminService.delete(id, locale);

        apiResponse.setMessage(this.messageUtilComponent.getMessage("admin.deleted.success", locale));
        apiResponse.setHttpStatus(HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }
}
