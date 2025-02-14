package com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePermissionVO {

    @NotNull(message = "{permission.id.required}")
    private Integer id;

    @NotNull(message = "{permission.role.id.required}")
    @Min(value = 1, message = "{permission.role.id.min}")
    private Integer roleId;

    @NotNull(message = "{permission.operation.id.required}")
    @Min(value = 1, message = "{permission.operation.id.min}")
    private Integer operationId;
}
