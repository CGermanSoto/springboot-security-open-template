// IUserDeveloperController.java
package com.spacecodee.springbootsecurityopentemplate.controller.api.user.developer;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.DeveloperDTO;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer.DeveloperUVO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IUserDeveloperController {
    @PostMapping()
    ResponseEntity<ApiResponsePojo> add(
            @RequestBody @Valid DeveloperAVO request,
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);

    @PutMapping("/{id}")
    ResponseEntity<ApiResponsePojo> update(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id,
            @RequestBody @Valid DeveloperUVO request);

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> delete(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id);

    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<DeveloperDTO>> findById(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id);

    @GetMapping()
    ResponseEntity<ApiResponseDataPojo<List<DeveloperDTO>>> findAll(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);
}