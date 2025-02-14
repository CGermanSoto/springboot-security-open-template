package com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PermissionDetailDTO extends PermissionDTO {

    private String roleName;

    private String operationTag;

    private String operationPath;

    private String operationHttpMethod;

}
