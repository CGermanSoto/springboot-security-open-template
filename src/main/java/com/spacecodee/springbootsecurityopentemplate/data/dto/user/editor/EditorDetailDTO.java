package com.spacecodee.springbootsecurityopentemplate.data.dto.user.editor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EditorDetailDTO extends EditorDTO {

    private Integer roleId;

    private String roleName;

}
