package com.spacecodee.springbootsecurityopentemplate.controller.api.user.customer.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.customer.IUserCustomerController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.CustomerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerAVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.customer.CustomerUVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageUtilComponent;
import com.spacecodee.springbootsecurityopentemplate.service.core.user.customer.IUserCustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-customer")
public class UserCustomerControllerImpl extends AbstractController implements IUserCustomerController {

    private final IUserCustomerService userClientService;

    public UserCustomerControllerImpl(MessageUtilComponent messageUtilComponent,
                                      IUserCustomerService userClientService) {
        super(messageUtilComponent);
        this.userClientService = userClientService;
    }

    @Override
    public ResponseEntity<ApiResponsePojo> add(CustomerAVO request, String locale) {
        this.userClientService.add(request, locale);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createResponse("customer.added.success", locale, HttpStatus.CREATED));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> update(String locale, int id, CustomerUVO request) {
        this.userClientService.update(id, request, locale);
        return ResponseEntity.ok(createResponse("customer.updated.success", locale, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> delete(String locale, int id) {
        this.userClientService.delete(id, locale);
        return ResponseEntity.ok(createResponse("customer.deleted.success", locale, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<CustomerDTO>> findById(String locale, int id) {
        return ResponseEntity.ok(createDataResponse(
                this.userClientService.findById(id, locale),
                "customer.found.success",
                locale,
                HttpStatus.OK));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<CustomerDTO>>> findAll(String locale) {
        return ResponseEntity.ok(createDataResponse(
                this.userClientService.findAll(locale),
                "customer.list.success",
                locale,
                HttpStatus.OK));
    }
}