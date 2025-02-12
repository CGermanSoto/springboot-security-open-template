package com.spacecodee.springbootsecurityopentemplate.data.dto.core.operation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperationDTO {

    private Integer id;

    private String tag;

    private String path;

    private String httpMethod;

    private Boolean permitAll;

    private Integer moduleId;

    private String moduleName;

}