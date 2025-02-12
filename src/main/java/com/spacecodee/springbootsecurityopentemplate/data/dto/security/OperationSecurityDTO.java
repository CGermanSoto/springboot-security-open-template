package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperationSecurityDTO {

    private int id;

    private String tag;

    private String path;

    private String httpMethod;

    private boolean permitAll;

    private ModuleSecurityDTO moduleSecurityDTO;

}
