package com.spacecodee.springbootsecurityopentemplate.controller.user;

import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.auth.AddAdminVO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IUserAdminController {

    @PostMapping()
    ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> add(@RequestBody @Valid AddAdminVO request, @RequestHeader(name = "Accept-Language", required = false) String locale);

    @PutMapping()
    ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> update(@RequestHeader(name = "Accept-Language", required = false) String locale);

    @DeleteMapping()
    ResponseEntity<ApiResponseDataPojo<AuthenticationResponsePojo>> delete(@RequestHeader(name = "Accept-Language", required = false) String locale);
}
