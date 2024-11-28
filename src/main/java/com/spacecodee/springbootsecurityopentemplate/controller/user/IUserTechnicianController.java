package com.spacecodee.springbootsecurityopentemplate.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.TechnicianDTO;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.pojo.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.TechnicianAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.TechnicianUVO;

import jakarta.validation.Valid;

public interface IUserTechnicianController {
    @PostMapping()
    ResponseEntity<ApiResponsePojo> add(
            @RequestBody @Valid TechnicianAVO request,
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);

    @PutMapping("/{id}")
    ResponseEntity<ApiResponsePojo> update(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id,
            @RequestBody @Valid TechnicianUVO request);

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponsePojo> delete(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id);

    @GetMapping("/{id}")
    ResponseEntity<ApiResponseDataPojo<TechnicianDTO>> findById(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale,
            @PathVariable int id);

    @GetMapping()
    ResponseEntity<ApiResponseDataPojo<List<TechnicianDTO>>> findAll(
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") String locale);
}