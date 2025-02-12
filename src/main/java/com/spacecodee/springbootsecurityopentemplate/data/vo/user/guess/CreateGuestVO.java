package com.spacecodee.springbootsecurityopentemplate.data.vo.user.guess;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateGuestVO {

    @NotBlank(message = "{guest.username.required}")
    @Size(min = 6, max = 15, message = "{guest.username.size}")
    @Pattern(regexp = "^\\w*$", message = "{guest.username.pattern}")
    private String username;

    @NotBlank(message = "{guest.password.required}")
    @Size(min = 8, max = 20, message = "{guest.password.size}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).*$",
            message = "{guest.password.pattern}")
    private String password;

    @NotBlank(message = "{guest.firstname.required}")
    @Size(max = 20, message = "{guest.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{guest.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{guest.lastname.required}")
    @Size(max = 20, message = "{guest.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{guest.lastname.pattern}")
    private String lastName;

    @NotBlank(message = "{guest.email.required}")
    @Email(message = "{guest.email.invalid}")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "{guest.email.pattern}")
    private String email;

    @Pattern(regexp = "^9\\d{8}$", message = "{guest.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{guest.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$",
            message = "{guest.profile.picture.pattern}")
    private String profilePicturePath;
}
