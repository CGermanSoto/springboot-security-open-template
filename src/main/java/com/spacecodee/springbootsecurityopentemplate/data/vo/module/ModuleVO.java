package com.spacecodee.springbootsecurityopentemplate.data.vo.module;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModuleVO {
    @NotBlank()
    private String name;

    @NotBlank()
    private String basePath;
}