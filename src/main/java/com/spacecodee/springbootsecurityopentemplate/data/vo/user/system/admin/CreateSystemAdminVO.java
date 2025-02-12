package com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateSystemAdminVO {

    @NotBlank(message = "{system.admin.username.required}")
    @Size(min = 6, max = 15, message = "{system.admin.username.size}")
    @Pattern(regexp = "^\\w*$", message = "{system.admin.username.pattern}")
    private String username;

    @NotBlank(message = "{system.admin.password.required}")
    @Size(min = 8, max = 20, message = "{system.admin.password.size}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).*$",
            message = "{system.admin.password.pattern}")
    private String password;

    @NotBlank(message = "{system.admin.firstname.required}")
    @Size(max = 20, message = "{system.admin.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{system.admin.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{system.admin.lastname.required}")
    @Size(max = 20, message = "{system.admin.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{system.admin.lastname.pattern}")
    private String lastName;

    @NotBlank(message = "{system.admin.email.required}")
    @Email(message = "{system.admin.email.invalid}")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "{system.admin.email.pattern}")
    private String email;

    @Pattern(regexp = "^9\\d{8}$", message = "{system.admin.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{system.admin.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$",
            message = "{system.admin.profile.picture.pattern}")
    private String profilePicturePath;
}
