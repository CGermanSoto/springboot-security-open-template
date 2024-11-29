package com.spacecodee.springbootsecurityopentemplate.data.vo.core;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModuleVO {
    @NotBlank(message = "{validation.module.name.required}")
    private String name;

    @NotBlank(message = "{validation.module.base.path.required}")
    private String basePath;
}