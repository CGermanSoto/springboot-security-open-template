// OperationVO.java
package com.spacecodee.springbootsecurityopentemplate.data.vo.operation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OperationVO {
    @NotBlank()
    private String tag;

    @NotBlank()
    private String path;

    @NotBlank()
    private String httpMethod;

    @NotNull()
    private Boolean permitAll;

    @NotNull()
    private Integer moduleId;
}