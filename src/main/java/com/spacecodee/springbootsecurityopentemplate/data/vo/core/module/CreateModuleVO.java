package com.spacecodee.springbootsecurityopentemplate.data.vo.core.module;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateModuleVO {
    @NotBlank(message = "{module.name.required}")
    @Size(max = 100, message = "{module.name.max.length}")
    @Pattern(regexp = "^[A-Za-z_]+$", message = "{module.name.pattern}")
    private String name;

    @NotBlank(message = "{module.base.path.required}")
    @Size(max = 100, message = "{module.base.path.max.length}")
    @Pattern(regexp = "^/[a-zA-Z-]+(?:/[a-zA-Z-]+)*+$", message = "{module.base.path.pattern}")
    private String basePath;
}