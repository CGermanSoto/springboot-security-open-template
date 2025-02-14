package com.spacecodee.springbootsecurityopentemplate.data.vo.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginVO {

    @NotEmpty(message = "{validation.login.username.required}")
    @Pattern(regexp = "^\\w+$", message = "{validation.login.username.pattern}")
    private String username;

    @NotEmpty(message = "{validation.login.password.required}")
    private String password;
}
