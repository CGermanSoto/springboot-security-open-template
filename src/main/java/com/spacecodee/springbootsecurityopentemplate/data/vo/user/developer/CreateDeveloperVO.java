package com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateDeveloperVO {

    @NotBlank(message = "{developer.username.required}")
    @Size(min = 6, max = 15, message = "{developer.username.size}")
    @Pattern(regexp = "^\\w*$", message = "{developer.username.pattern}")
    private String username;

    @NotBlank(message = "{developer.password.required}")
    @Size(min = 8, max = 20, message = "{developer.password.size}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).*$", message = "{developer.password.pattern}")
    private String password;

    @NotBlank(message = "{developer.firstname.required}")
    @Size(max = 20, message = "{developer.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{developer.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{developer.lastname.required}")
    @Size(max = 20, message = "{developer.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{developer.lastname.pattern}")
    private String lastName;

    @NotBlank(message = "{developer.email.required}")
    @Email(message = "{developer.email.invalid}")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "{developer.email.pattern}")
    private String email;

    @Pattern(regexp = "^9\\d{8}$", message = "{developer.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{developer.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$", message = "{developer.profile.picture.pattern}")
    private String profilePicturePath;
}
