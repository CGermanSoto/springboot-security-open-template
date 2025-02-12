package com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateEditorVO {

    @NotBlank(message = "{editor.username.required}")
    @Size(min = 6, max = 15, message = "{editor.username.size}")
    @Pattern(regexp = "^\\w*$", message = "{editor.username.pattern}")
    private String username;

    @NotBlank(message = "{editor.password.required}")
    @Size(min = 8, max = 20, message = "{editor.password.size}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).*$",
            message = "{editor.password.pattern}")
    private String password;

    @NotBlank(message = "{editor.firstname.required}")
    @Size(max = 20, message = "{editor.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{editor.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{editor.lastname.required}")
    @Size(max = 20, message = "{editor.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{editor.lastname.pattern}")
    private String lastName;

    @NotBlank(message = "{editor.email.required}")
    @Email(message = "{editor.email.invalid}")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "{editor.email.pattern}")
    private String email;

    @Pattern(regexp = "^9\\d{8}$", message = "{editor.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{editor.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$",
            message = "{editor.profile.picture.pattern}")
    private String profilePicturePath;
}
