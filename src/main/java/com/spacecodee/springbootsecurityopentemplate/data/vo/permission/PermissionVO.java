// PermissionVO.java
package com.spacecodee.springbootsecurityopentemplate.data.vo.permission;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissionVO {
    @NotNull()
    private Integer roleId;

    @NotNull()
    private Integer operationId;
}