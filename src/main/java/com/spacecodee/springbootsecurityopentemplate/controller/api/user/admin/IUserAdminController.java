package com.spacecodee.springbootsecurityopentemplate.controller.api.user.admin;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin.AdminUVO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IUserAdminController {

    @PostMapping()
    ResponseEntity<ApiResponsePojo> add(@RequestBody @Valid AdminAVO request,
                                        @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);

    @PutMapping("/{id}")
    ResponseEntity<ApiResponsePojo> update(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id, @RequestBody @Valid AdminUVO request);

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> delete(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id);
}
