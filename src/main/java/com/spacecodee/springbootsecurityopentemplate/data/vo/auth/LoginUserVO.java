package com.spacecodee.springbootsecurityopentemplate.data.vo.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserVO(
                @NotBlank(message = "{validation.login.username.required}") @Size(min = 3, message = "{validation.login.username.size}") String username,

                @NotBlank(message = "{validation.login.password.required}") @Size(min = 6, message = "{validation.login.password.size}") String password) {
}
