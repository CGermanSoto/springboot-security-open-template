package com.spacecodee.springbootsecurityopentemplate.data.dto.core.permission;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionDTO {

    private Integer id;

    private Integer roleId;

    private Integer operationId;

}
