package com.spacecodee.springbootsecurityopentemplate.controller.impl.user;

import com.spacecodee.springbootsecurityopentemplate.controller.user.IUserAdminController;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.AddAdminVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.user.IUserAdminService;
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
    public ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> add(AddAdminVO request, String locale) {
        var apiResponse = new ApiResponseDataPojo<AuthenticationResponsePojo>();
        this.userAdminService.add(request, locale);

        apiResponse.setMessage(this.messageUtilComponent.getMessage("admin.added.success", locale));
        apiResponse.setHttpStatus(HttpStatus.CREATED);

        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> update(String locale) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> delete(String locale) {
        return null;
    }
}
