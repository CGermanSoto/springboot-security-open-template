package com.spacecodee.springbootsecurityopentemplate.controller.api.user.customer;

import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.CustomerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerUVO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IUserCustomerController {
    @PostMapping()
    ResponseEntity<ApiResponsePojo> add(
            @RequestBody @Valid CustomerAVO request,
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);

    @PutMapping("/{id}")
    ResponseEntity<ApiResponsePojo> update(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id,
            @RequestBody @Valid CustomerUVO request);

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> delete(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id);

    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<CustomerDTO>> findById(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id);

    @GetMapping()
    ResponseEntity<ApiResponseDataPojo<List<CustomerDTO>>> findAll(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);
}