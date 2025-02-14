package com.spacecodee.springbootsecurityopentemplate.data.vo.user.developer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateDeveloperVO {

    private Integer id;

    @NotBlank(message = "{developer.firstname.required}")
    @Size(max = 20, message = "{developer.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{developer.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{developer.lastname.required}")
    @Size(max = 20, message = "{developer.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{developer.lastname.pattern}")
    private String lastName;

    @Pattern(regexp = "^9\\d{8}$", message = "{developer.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{developer.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$", message = "{developer.profile.picture.pattern}")
    private String profilePicturePath;

    @Pattern(regexp = "^(true|false)$", message = "{developer.status.pattern}")
    private String status;
}
