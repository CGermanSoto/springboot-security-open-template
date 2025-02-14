package com.spacecodee.springbootsecurityopentemplate.controller.api.core.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.core.IOperationController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation.OperationDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation.OperationDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.CreateOperationVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.OperationFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation.UpdateOperationVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.core.operation.IOperationService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/security/operation")
public class OperationControllerImpl extends AbstractController implements IOperationController {

    private final IOperationService operationService;

    public OperationControllerImpl(MessageParameterHandler messageParameterHandler, IOperationService operationService) {
        super(messageParameterHandler);
        this.operationService = operationService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<OperationDTO>> createOperation(CreateOperationVO request) {
        OperationDTO createdOperation = this.operationService.createOperation(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        createdOperation,
                        "operation.create.success",
                        HttpStatus.CREATED,
                        createdOperation.getTag()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<OperationDTO>> updateOperation(Integer id,
                                                                             @NotNull UpdateOperationVO request) {
        request.setId(id);
        OperationDTO updatedOperation = this.operationService.updateOperation(request);
        return ResponseEntity.ok(super.createDataResponse(
                updatedOperation,
                "operation.update.success",
                HttpStatus.OK,
                updatedOperation.getTag()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<OperationDTO>> getOperationById(Integer id) {
        OperationDTO operation = this.operationService.getOperationById(id);
        return ResponseEntity.ok(super.createDataResponse(
                operation,
                "operation.found.success",
                HttpStatus.OK,
                operation.getTag()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<OperationDetailDTO>> getOperationDetailById(Integer id) {
        OperationDetailDTO operation = this.operationService.getOperationDetailById(id);
        return ResponseEntity.ok(super.createDataResponse(
                operation,
                "operation.detail.found.success",
                HttpStatus.OK,
                operation.getTag()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Page<OperationDTO>>> searchOperations(OperationFilterVO filterVO) {
        Page<OperationDTO> operations = this.operationService.searchOperations(filterVO);
        return ResponseEntity.ok(super.createDataResponse(
                operations,
                "operation.search.success",
                HttpStatus.OK,
                operations.getTotalElements()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<OperationDTO>>> getAllOperationsByModuleId(Integer moduleId) {
        List<OperationDTO> operations = this.operationService.getAllOperationsByModuleId(moduleId);
        return ResponseEntity.ok(super.createDataResponse(
                operations,
                "operation.module.list.success",
                HttpStatus.OK,
                operations.size()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<OperationDTO>>> getAllOperations() {
        List<OperationDTO> operations = this.operationService.getAllOperations();
        return ResponseEntity.ok(super.createDataResponse(
                operations,
                "operation.list.success",
                HttpStatus.OK,
                operations.size()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> deleteOperation(Integer id) {
        OperationDTO operation = this.operationService.getOperationById(id);
        this.operationService.deleteOperation(id);
        return ResponseEntity.ok(super.createResponse(
                "operation.delete.success",
                HttpStatus.OK,
                operation.getTag()));
    }

}
