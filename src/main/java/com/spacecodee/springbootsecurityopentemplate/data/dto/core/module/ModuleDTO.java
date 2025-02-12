package com.spacecodee.springbootsecurityopentemplate.data.dto.core.module;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleDTO {

    private Integer id;

    private String name;

    private String basePath;

}