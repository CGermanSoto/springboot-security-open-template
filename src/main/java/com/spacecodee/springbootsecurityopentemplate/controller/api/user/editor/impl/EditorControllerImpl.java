package com.spacecodee.springbootsecurityopentemplate.controller.api.user.editor.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.editor.IEditorController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor.EditorDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor.EditorDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.CreateEditorVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.EditorFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.UpdateEditorVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.user.editor.IEditorService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/editor")
public class EditorControllerImpl extends AbstractController implements IEditorController {

    private final IEditorService editorService;

    public EditorControllerImpl(MessageParameterHandler messageParameterHandler, IEditorService editorService) {
        super(messageParameterHandler);
        this.editorService = editorService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<EditorDTO>> createEditor(CreateEditorVO request) {
        EditorDTO createdEditor = this.editorService.createEditor(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        createdEditor,
                        "editor.create.success",
                        HttpStatus.CREATED,
                        createdEditor.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<EditorDTO>> updateEditor(Integer id, @NotNull UpdateEditorVO request) {
        request.setId(id);
        EditorDTO updatedEditor = this.editorService.updateEditor(request);
        return ResponseEntity.ok(super.createDataResponse(
                updatedEditor,
                "editor.update.success",
                HttpStatus.OK,
                updatedEditor.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<EditorDTO>> getEditorById(Integer id) {
        EditorDTO editorDTO = this.editorService.getEditorById(id);
        return ResponseEntity.ok(super.createDataResponse(
                editorDTO,
                "editor.found.success",
                HttpStatus.OK,
                editorDTO.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<EditorDetailDTO>> getEditorDetailById(Integer id) {
        EditorDetailDTO editorDetail = this.editorService.getEditorDetailById(id);
        return ResponseEntity.ok(super.createDataResponse(
                editorDetail,
                "editor.detail.found.success",
                HttpStatus.OK,
                editorDetail.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Page<EditorDTO>>> searchEditors(EditorFilterVO filterVO) {
        Page<EditorDTO> editorsPage = this.editorService.searchEditors(filterVO);
        return ResponseEntity.ok(super.createDataResponse(
                editorsPage,
                "editor.search.success",
                HttpStatus.OK,
                editorsPage.getTotalElements()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<EditorDTO>> changeEditorStatus(Integer id, Boolean status) {
        EditorDTO updatedEditor = this.editorService.changeEditorStatus(id, status);
        return ResponseEntity.ok(super.createDataResponse(
                updatedEditor,
                "editor.status.change.success",
                HttpStatus.OK,
                updatedEditor.getUsername(),
                status));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> deleteEditor(Integer id) {
        this.editorService.deleteEditor(id);
        return ResponseEntity.ok(super.createResponse(
                "editor.delete.success",
                HttpStatus.OK,
                id.toString()));
    }
    
}
