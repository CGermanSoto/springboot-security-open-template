package com.spacecodee.springbootsecurityopentemplate.data.vo.user.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

import com.spacecodee.springbootsecurityopentemplate.constants.ValidationConstants;

@Data
public abstract class BaseUserVO implements Serializable {
    @NotBlank(message = "{validation.user.username.required}")
    @Size(min = ValidationConstants.MIN_USERNAME_LENGTH, max = ValidationConstants.MAX_USERNAME_LENGTH, message = "{"
            + ValidationConstants.Messages.USERNAME_SIZE + "}")
    private String username;

    @NotBlank(message = "{validation.user.fullname.required}")
    @Size(min = ValidationConstants.MIN_NAME_LENGTH, max = ValidationConstants.MAX_NAME_LENGTH, message = "{"
            + ValidationConstants.Messages.FULLNAME_SIZE + "}")
    private String fullname;

    @NotBlank(message = "{validation.user.lastname.required}")
    @Size(min = ValidationConstants.MIN_NAME_LENGTH, max = ValidationConstants.MAX_NAME_LENGTH, message = "{"
            + ValidationConstants.Messages.LASTNAME_SIZE + "}")
    private String lastname;
}