package com.spacecodee.springbootsecurityopentemplate.data.vo.core.module;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleFilterVO {

    @Pattern(regexp = "^[a-zA-Z_]*$", message = "{module.name.pattern}")
    private String name;

    @Pattern(regexp = "^/[a-zA-Z-]+(?:/[a-zA-Z-]+)*+$", message = "{module.base.path.pattern}")
    private String basePath;

    @Min(value = 0, message = "{module.page.min}")
    private Integer page = 0;

    @Min(value = 1, message = "{module.size.min}")
    @Max(value = 100, message = "{module.size.max}")
    private Integer size = 10;

    @Pattern(regexp = "^(name|basePath)$", message = "{module.sort.invalid}")
    private String sortBy = "name";

    @Pattern(regexp = "^(ASC|DESC)$", message = "{module.sort.direction.invalid}")
    private String sortDirection = "ASC";
}