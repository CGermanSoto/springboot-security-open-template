package com.spacecodee.springbootsecurityopentemplate.data.vo.user.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// AdminAVO.java
public record AdminAVO(
        @NotBlank(message = "{validation.field.required}") @Size(min = 3, message = "{validation.field.min.length}") String username,

        @NotBlank(message = "{validation.field.required}") @Size(min = 6, message = "{validation.field.min.length}") String password,

        @NotBlank(message = "{validation.field.required}") @Size(min = 6, message = "{validation.field.min.length}") String repeatPassword,

        @NotBlank(message = "{validation.field.required}") String fullname,

        @NotBlank(message = "{validation.field.required}") String lastname) {
}
