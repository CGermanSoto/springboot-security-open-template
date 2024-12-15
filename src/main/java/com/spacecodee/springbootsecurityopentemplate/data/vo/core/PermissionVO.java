package com.spacecodee.springbootsecurityopentemplate.data.vo.core;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PermissionVO {
    @NotNull(message = "{validation.permission.role.required," +
            "${validatedValue}")
    @Min(value = 1, message = "{validation.permission.role.min," +
            "${validatedValue}")
    private Integer roleId;

    @NotNull(message = "{validation.permission.operation.required," +
            "${validatedValue}")
    @Min(value = 1, message = "{validation.permission.operation.min," +
            "${validatedValue}")
    private Integer operationId;
}