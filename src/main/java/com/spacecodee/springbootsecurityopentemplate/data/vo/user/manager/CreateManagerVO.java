package com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateManagerVO {

    @NotBlank(message = "{manager.username.required}")
    @Size(min = 6, max = 15, message = "{manager.username.size}")
    @Pattern(regexp = "^\\w*$", message = "{manager.username.pattern}")
    private String username;

    @NotBlank(message = "{manager.password.required}")
    @Size(min = 8, max = 20, message = "{manager.password.size}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).*$",
            message = "{manager.password.pattern}")
    private String password;

    @NotBlank(message = "{manager.firstname.required}")
    @Size(max = 20, message = "{manager.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{manager.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{manager.lastname.required}")
    @Size(max = 20, message = "{manager.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{manager.lastname.pattern}")
    private String lastName;

    @NotBlank(message = "{manager.email.required}")
    @Email(message = "{manager.email.invalid}")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "{manager.email.pattern}")
    private String email;

    @Pattern(regexp = "^9\\d{8}$", message = "{manager.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{manager.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$", message = "{manager.profile.picture.pattern}")
    private String profilePicturePath;
}
