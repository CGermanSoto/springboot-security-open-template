package com.spacecodee.springbootsecurityopentemplate.data.vo.core.permission;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionFilterVO {

    @Min(value = 1, message = "{permission.filter.role.id.min}")
    private Integer roleId;

    @Min(value = 1, message = "{permission.filter.operation.id.min}")
    private Integer operationId;

    @Min(value = 0, message = "{permission.page.min}")
    private Integer page = 0;

    @Min(value = 1, message = "{permission.size.min}")
    @Max(value = 100, message = "{permission.size.max}")
    private Integer size = 10;

    @Pattern(regexp = "^(roleId|operationId)$", message = "{permission.sort.invalid}")
    private String sortBy = "roleId";

    @Pattern(regexp = "^(ASC|DESC)$", message = "{permission.sort.direction.invalid}")
    private String sortDirection = "ASC";
}
