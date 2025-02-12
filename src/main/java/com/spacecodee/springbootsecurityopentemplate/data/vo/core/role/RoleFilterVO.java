package com.spacecodee.springbootsecurityopentemplate.data.vo.core.role;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleFilterVO {

    @Pattern(regexp = "^[A-Z_]*$", message = "{role.filter.name.pattern}")
    private String name;

    @Min(value = 0, message = "{role.page.min}")
    private Integer page = 0;

    @Min(value = 1, message = "{role.size.min}")
    @Max(value = 100, message = "{role.size.max}")
    private Integer size = 10;

    @Pattern(regexp = "^(name)$", message = "{role.sort.invalid}")
    private String sortBy = "name";

    @Pattern(regexp = "^(ASC|DESC)$", message = "{role.sort.direction.invalid}")
    private String sortDirection = "ASC";

}