package com.spacecodee.springbootsecurityopentemplate.data.vo.user.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseUserAddVO extends BaseUserVO {
    @NotBlank(message = "{validation.user.password.required}")
    @Size(min = 6, message = "{validation.user.password.size}")
    private String password;

    @NotBlank(message = "{validation.user.repeat.password.required}")
    @Size(min = 6, message = "{validation.user.repeat.password.size}")
    private String repeatPassword;
}
