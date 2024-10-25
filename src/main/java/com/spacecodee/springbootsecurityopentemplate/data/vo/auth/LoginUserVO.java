package com.spacecodee.springbootsecurityopentemplate.data.vo.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(min = 4)
    @NotEmpty
    @NotBlank
    private String username;

    @Size(min = 6)
    @NotEmpty
    @NotBlank
    private String password;
}
