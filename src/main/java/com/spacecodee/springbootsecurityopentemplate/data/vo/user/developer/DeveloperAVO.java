package com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeveloperAVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{validation.field.required}")
    @Size(min = 3, message = "{validation.field.min.length}")
    private String username;

    @NotBlank(message = "{validation.field.required}")
    @Size(min = 6, message = "{validation.field.min.length}")
    private String password;

    @NotBlank(message = "{validation.field.required}")
    @Size(min = 6, message = "{validation.field.min.length}")
    private String repeatPassword;

    @NotBlank(message = "{validation.field.required}")
    @Size(min = 1, message = "{validation.field.min.length}")
    private String fullname;

    @NotBlank(message = "{validation.field.required}")
    @Size(min = 1, message = "{validation.field.min.length}")
    private String lastname;
}