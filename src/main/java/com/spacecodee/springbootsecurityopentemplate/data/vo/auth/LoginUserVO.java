package com.spacecodee.springbootsecurityopentemplate.data.vo.auth;

import com.spacecodee.springbootsecurityopentemplate.constants.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserVO(
        @NotBlank(message = "{validation.login.username.required}") @Size(min = ValidationConstants.MIN_USERNAME_LENGTH, max = ValidationConstants.MAX_USERNAME_LENGTH, message = "{validation.user.username.size}") String username,

        @NotBlank(message = "{validation.login.password.required}") @Size(min = ValidationConstants.MIN_PASSWORD_LENGTH, max = ValidationConstants.MAX_PASSWORD_LENGTH, message = "{validation.user.password.size}") String password) {
}