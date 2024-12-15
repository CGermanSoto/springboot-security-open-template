package com.spacecodee.springbootsecurityopentemplate.data.vo.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.spacecodee.springbootsecurityopentemplate.constants.ValidationConstants;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class OperationVO {
    @NotBlank(message = "{validation.operation.tag.required," +
            "${validatedValue}")
    @Size(min = ValidationConstants.MIN_NAME_LENGTH, max = ValidationConstants.MAX_NAME_LENGTH, message = "{validation.operation.tag.size,"
            +
            "${validatedValue}," +
            "${min}," +
            "${max}}")
    private String tag;

    @Pattern(regexp = "^$|^/.*$", message = "{validation.operation.path.pattern," +
            "${validatedValue}")
    private String path;

    @NotBlank(message = "{validation.operation.method.required," +
            "${validatedValue}")
    @Pattern(regexp = "^(GET|POST|PUT|DELETE|PATCH)$", message = "{validation.operation.method.pattern," +
            "${validatedValue}")
    private String httpMethod;

    @NotNull(message = "{validation.operation.permit.all.required," +
            "${validatedValue}")
    private Boolean permitAll;

    @NotNull(message = "{validation.operation.module.required," +
            "${validatedValue}")
    @Min(value = 1, message = "{validation.operation.module.min," +
            "${validatedValue}")
    private Integer moduleId;
}