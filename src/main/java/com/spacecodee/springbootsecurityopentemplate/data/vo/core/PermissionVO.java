package com.spacecodee.springbootsecurityopentemplate.data.vo.core;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissionVO {
    @NotNull(message = "{validation.field.required}")
    private Integer roleId;

    @NotNull(message = "{validation.field.required}")
    private Integer operationId;
}