package com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateGuestVO {

    private Integer id;

    @NotBlank(message = "{guest.firstname.required}")
    @Size(max = 20, message = "{guest.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{guest.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{guest.lastname.required}")
    @Size(max = 20, message = "{guest.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{guest.lastname.pattern}")
    private String lastName;

    @Pattern(regexp = "^9\\d{8}$", message = "{guest.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{guest.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$",
            message = "{guest.profile.picture.pattern}")
    private String profilePicturePath;

    @Pattern(regexp = "^(true|false)$", message = "{guest.status.pattern}")
    private String status;
}
