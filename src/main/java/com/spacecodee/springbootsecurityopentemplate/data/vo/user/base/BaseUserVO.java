package com.spacecodee.springbootsecurityopentemplate.data.vo.user.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseUserVO implements Serializable {
    @NotBlank(message = "{validation.field.required}")
    @Size(min = 3, message = "{validation.field.min.length}")
    private String username;

    @NotBlank(message = "{validation.field.required}")
    @Size(min = 1, message = "{validation.field.min.length}")
    private String fullname;

    @NotBlank(message = "{validation.field.required}")
    @Size(min = 1, message = "{validation.field.min.length}")
    private String lastname;
}