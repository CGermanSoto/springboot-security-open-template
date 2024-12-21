package com.spacecodee.springbootsecurityopentemplate.data.vo.user.base;

import com.spacecodee.springbootsecurityopentemplate.constants.ValidationConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseUserAddVO extends BaseUserUpdateVO {
        @NotBlank(message = "{validation.user.password.required}")
        @Size(min = ValidationConstants.MIN_PASSWORD_LENGTH, max = ValidationConstants.MAX_PASSWORD_LENGTH, message = "{validation.user.password.size,"
                        +
                        "${validatedValue}," +
                        "${min}," +
                        "${max}}")
        private String password;

        @NotBlank(message = "{validation.user.repeat.password.required}")
        @Size(min = ValidationConstants.MIN_PASSWORD_LENGTH, max = ValidationConstants.MAX_PASSWORD_LENGTH, message = "{validation.user.repeat.password.size,"
                        +
                        "${validatedValue}," +
                        "${min}," +
                        "${max}}")
        private String repeatPassword;
}
