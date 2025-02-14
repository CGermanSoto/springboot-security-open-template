package com.spacecodee.springbootsecurityopentemplate.service.user.viewer;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer.ViewerDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer.ViewerDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.CreateViewerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.UpdateViewerVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer.ViewerFilterVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface IViewerService {

    ViewerDTO createViewer(CreateViewerVO createViewerVO);

    ViewerDTO updateViewer(UpdateViewerVO updateViewerVO);

    ViewerDTO getViewerById(Integer id);

    ViewerDetailDTO getViewerDetailById(Integer id);

    UserEntity getViewerEntityById(Integer id);

    Page<ViewerDTO> searchViewers(ViewerFilterVO filterVO);

    ViewerDTO changeViewerStatus(Integer id, Boolean status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteViewer(Integer id);
    
}
