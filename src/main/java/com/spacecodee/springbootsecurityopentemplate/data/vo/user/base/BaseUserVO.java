package com.spacecodee.springbootsecurityopentemplate.data.vo.user.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseUserVO implements Serializable {
    @NotBlank(message = "{validation.user.username.required}")
    @Size(min = 3, message = "{validation.user.username.size}")
    private String username;

    @NotBlank(message = "{validation.user.fullname.required}")
    @Size(min = 1, message = "{validation.user.fullname.size}")
    private String fullname;

    @NotBlank(message = "{validation.user.lastname.required}")
    @Size(min = 1, message = "{validation.user.lastname.size}")
    private String lastname;
}