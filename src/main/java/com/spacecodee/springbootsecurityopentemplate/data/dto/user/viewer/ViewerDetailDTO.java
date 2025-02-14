package com.spacecodee.springbootsecurityopentemplate.data.dto.user.viewer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ViewerDetailDTO extends ViewerDTO {

    private Integer roleId;

    private String roleName;

}
