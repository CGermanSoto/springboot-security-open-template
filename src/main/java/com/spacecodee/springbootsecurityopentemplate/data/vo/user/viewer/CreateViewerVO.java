package com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateViewerVO {

    @NotBlank(message = "{viewer.username.required}")
    @Size(min = 6, max = 15, message = "{viewer.username.size}")
    @Pattern(regexp = "^\\w*$", message = "{viewer.username.pattern}")
    private String username;

    @NotBlank(message = "{viewer.password.required}")
    @Size(min = 8, max = 20, message = "{viewer.password.size}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).*$",
            message = "{viewer.password.pattern}")
    private String password;

    @NotBlank(message = "{viewer.firstname.required}")
    @Size(max = 20, message = "{viewer.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{viewer.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{viewer.lastname.required}")
    @Size(max = 20, message = "{viewer.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{viewer.lastname.pattern}")
    private String lastName;

    @NotBlank(message = "{viewer.email.required}")
    @Email(message = "{viewer.email.invalid}")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "{viewer.email.pattern}")
    private String email;

    @Pattern(regexp = "^9\\d{8}$", message = "{viewer.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{viewer.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$",
            message = "{viewer.profile.picture.pattern}")
    private String profilePicturePath;
}
