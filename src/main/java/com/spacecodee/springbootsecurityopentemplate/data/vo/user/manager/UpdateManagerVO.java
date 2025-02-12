package com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateManagerVO {

    private Integer id;

    @NotBlank(message = "{manager.firstname.required}")
    @Size(max = 20, message = "{manager.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{manager.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{manager.lastname.required}")
    @Size(max = 20, message = "{manager.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{manager.lastname.pattern}")
    private String lastName;

    @Pattern(regexp = "^9\\d{8}$", message = "{manager.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{manager.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$",
            message = "{manager.profile.picture.pattern}")
    private String profilePicturePath;

    @Pattern(regexp = "^(true|false)$", message = "{manager.status.pattern}")
    private String status;
}
