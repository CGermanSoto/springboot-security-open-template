package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleSecurityDTO {

    private int id;

    private String name;

    private String basePath;

}
