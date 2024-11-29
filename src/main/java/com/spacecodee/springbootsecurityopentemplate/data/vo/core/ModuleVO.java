package com.spacecodee.springbootsecurityopentemplate.data.vo.core;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModuleVO {
    @NotBlank(message = "{validation.field.required}")
    private String name;

    @NotBlank(message = "{validation.field.required}")
    private String basePath;
}