// OperationVO.java
package com.spacecodee.springbootsecurityopentemplate.data.vo.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OperationVO {
    @NotBlank(message = "{validation.field.required}")
    private String tag;

    @NotBlank(message = "{validation.field.required}")
    private String path;

    @NotBlank(message = "{validation.field.required}")
    private String httpMethod;

    @NotNull(message = "{validation.field.required}")
    private Boolean permitAll;

    @NotNull(message = "{validation.field.required}")
    private Integer moduleId;
}