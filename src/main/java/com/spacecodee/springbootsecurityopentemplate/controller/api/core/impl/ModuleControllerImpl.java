package com.spacecodee.springbootsecurityopentemplate.controller.api.core.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.core.IModuleController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.core.module.ModuleDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.CreateModuleVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.ModuleFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.core.module.UpdateModuleVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.core.module.IModuleService;
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
@RequestMapping("/security/module")
public class ModuleControllerImpl extends AbstractController implements IModuleController {

    private final IModuleService moduleService;

    public ModuleControllerImpl(MessageParameterHandler messageParameterHandler, IModuleService moduleService) {
        super(messageParameterHandler);
        this.moduleService = moduleService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ModuleDTO>> createModule(CreateModuleVO request) {
        ModuleDTO createdModule = this.moduleService.createModule(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        createdModule,
                        "module.create.success",
                        HttpStatus.CREATED,
                        createdModule.getName()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ModuleDTO>> updateModule(Integer id, @NotNull UpdateModuleVO updateModuleVO) {
        updateModuleVO.setId(id);
        ModuleDTO updatedModule = this.moduleService.updateModule(updateModuleVO);
        return ResponseEntity.ok(super.createDataResponse(
                updatedModule,
                "module.update.success",
                HttpStatus.OK,
                updatedModule.getName()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ModuleDTO>> getModuleById(Integer id) {
        ModuleDTO module = this.moduleService.getModuleById(id);
        return ResponseEntity.ok(super.createDataResponse(
                module,
                "module.found.success",
                HttpStatus.OK,
                module.getName()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Page<ModuleDTO>>> searchModules(ModuleFilterVO filterVO) {
        Page<ModuleDTO> modules = this.moduleService.searchModules(filterVO);
        return ResponseEntity.ok(super.createDataResponse(
                modules,
                "module.search.success",
                HttpStatus.OK,
                modules.getTotalElements()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<List<ModuleDTO>>> getAllModules() {
        List<ModuleDTO> modules = this.moduleService.getAllModules();
        return ResponseEntity.ok(super.createDataResponse(
                modules,
                "module.list.success",
                HttpStatus.OK,
                modules.size()));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> deleteModule(Integer id) {
        ModuleDTO module = this.moduleService.getModuleById(id);
        this.moduleService.deleteModule(id);
        return ResponseEntity.ok(super.createResponse(
                "module.delete.success",
                HttpStatus.OK,
                module.getName()));
    }

}
