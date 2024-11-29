// OperationVO.java
package com.spacecodee.springbootsecurityopentemplate.data.vo.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OperationVO {
    @NotBlank(message = "{validation.operation.tag.required}")
    private String tag;

    @NotBlank(message = "{validation.operation.path.required}")
    private String path;

    @NotBlank(message = "{validation.operation.method.required}")
    private String httpMethod;

    @NotNull(message = "{validation.operation.permit.all.required}")
    private Boolean permitAll;

    @NotNull(message = "{validation.operation.module.required}")
    private Integer moduleId;
}