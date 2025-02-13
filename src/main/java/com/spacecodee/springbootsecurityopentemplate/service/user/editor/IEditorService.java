package com.spacecodee.springbootsecurityopentemplate.service.user.editor;

import com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor.EditorDTO;
import com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor.EditorDetailDTO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.CreateEditorVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.EditorFilterVO;
import com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor.UpdateEditorVO;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface IEditorService {

    EditorDTO createEditor(CreateEditorVO createEditorVO);

    EditorDTO updateEditor(UpdateEditorVO updateEditorVO);

    EditorDTO getEditorById(Integer id);

    EditorDetailDTO getEditorDetailById(Integer id);

    UserEntity getEditorEntityById(Integer id);

    Page<EditorDTO> searchEditors(EditorFilterVO filterVO);

    EditorDTO changeEditorStatus(Integer id, Boolean status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteEditor(Integer id);

}
