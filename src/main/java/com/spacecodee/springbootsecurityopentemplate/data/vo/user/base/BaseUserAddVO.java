package com.spacecodee.springbootsecurityopentemplate.data.vo.user.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseUserAddVO extends BaseUserVO {
    @NotBlank(message = "{validation.field.required}")
    @Size(min = 6, message = "{validation.field.min.length}")
    private String password;

    @NotBlank(message = "{validation.field.required}")
    @Size(min = 6, message = "{validation.field.min.length}")
    private String repeatPassword;
}
