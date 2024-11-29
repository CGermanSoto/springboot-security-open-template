package com.spacecodee.springbootsecurityopentemplate.data.vo.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserVO(
        @NotBlank(message = "{validation.field.required}") @Size(min = 3, message = "{validation.field.min.length}") String username,

        @NotBlank(message = "{validation.field.required}") @Size(min = 6, message = "{validation.field.min.length}") String password) {
}
