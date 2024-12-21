package com.spacecodee.springbootsecurityopentemplate.data.vo.core;

import com.spacecodee.springbootsecurityopentemplate.constants.ValidationConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ModuleVO {
    @NotBlank(message = "{validation.module.name.required," +
            "${validatedValue}")
    @Size(min = ValidationConstants.MIN_NAME_LENGTH, max = ValidationConstants.MAX_NAME_LENGTH, message = "{validation.module.name.size,"
            +
            "${validatedValue}," +
            "${min}," +
            "${max}}")
    private String name;

    @Pattern(regexp = "^$|^/.*$", message = "{validation.module.base.path.pattern," +
            "${validatedValue}")
    private String basePath;
}