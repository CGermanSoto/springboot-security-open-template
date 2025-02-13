package com.spacecodee.springbootsecurityopentemplate.controller.api.user.viewer.impl;

import com.spacecodee.springbootsecurityopentemplate.controller.api.user.viewer.IViewerController;
import com.spacecodee.springbootsecurityopentemplate.controller.base.AbstractController;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponseDataPojo;
import com.spacecodee.springbootsecurityopentemplate.data.common.response.ApiResponsePojo;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer.ViewerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer.ViewerDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.CreateViewerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.UpdateViewerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.ViewerFilterVO;
import com.spacecodee.springbootsecurityopentemplate.language.MessageParameterHandler;
import com.spacecodee.springbootsecurityopentemplate.service.user.viewer.IViewerService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/viewer")
public class ViewerControllerImpl extends AbstractController implements IViewerController {

    private final IViewerService viewerService;

    public ViewerControllerImpl(MessageParameterHandler messageParameterHandler, IViewerService viewerService) {
        super(messageParameterHandler);
        this.viewerService = viewerService;
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ViewerDTO>> createViewer(CreateViewerVO request) {
        ViewerDTO createdViewer = this.viewerService.createViewer(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(super.createDataResponse(
                        createdViewer,
                        "viewer.create.success",
                        HttpStatus.CREATED,
                        createdViewer.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ViewerDTO>> updateViewer(Integer id, @NotNull UpdateViewerVO request) {
        request.setId(id);
        ViewerDTO updatedViewer = this.viewerService.updateViewer(request);
        return ResponseEntity.ok(super.createDataResponse(
                updatedViewer,
                "viewer.update.success",
                HttpStatus.OK,
                updatedViewer.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ViewerDTO>> getViewerById(Integer id) {
        ViewerDTO viewer = this.viewerService.getViewerById(id);
        return ResponseEntity.ok(super.createDataResponse(
                viewer,
                "viewer.found.success",
                HttpStatus.OK,
                viewer.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ViewerDetailDTO>> getViewerDetailById(Integer id) {
        ViewerDetailDTO viewerDetail = this.viewerService.getViewerDetailById(id);
        return ResponseEntity.ok(super.createDataResponse(
                viewerDetail,
                "viewer.detail.found.success",
                HttpStatus.OK,
                viewerDetail.getUsername()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<Page<ViewerDTO>>> searchViewers(ViewerFilterVO filterVO) {
        Page<ViewerDTO> viewers = this.viewerService.searchViewers(filterVO);
        return ResponseEntity.ok(super.createDataResponse(
                viewers,
                "viewer.search.success",
                HttpStatus.OK,
                viewers.getTotalElements()));
    }

    @Override
    public ResponseEntity<ApiResponseDataPojo<ViewerDTO>> changeViewerStatus(Integer id, Boolean status) {
        ViewerDTO updatedViewer = this.viewerService.changeViewerStatus(id, status);
        return ResponseEntity.ok(super.createDataResponse(
                updatedViewer,
                "viewer.status.change.success",
                HttpStatus.OK,
                updatedViewer.getUsername(),
                status));
    }

    @Override
    public ResponseEntity<ApiResponsePojo> deleteViewer(Integer id) {
        this.viewerService.deleteViewer(id);
        return ResponseEntity.ok(super.createResponse(
                "viewer.delete.success",
                HttpStatus.OK,
                id.toString()));
    }

}
