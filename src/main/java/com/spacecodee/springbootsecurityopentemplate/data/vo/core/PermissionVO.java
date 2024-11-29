package com.spacecodee.springbootsecurityopentemplate.data.vo.core;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissionVO {
    @NotNull(message = "{validation.permission.role.required}")
    private Integer roleId;

    @NotNull(message = "{validation.permission.operation.required}")
    private Integer operationId;
}