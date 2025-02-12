package com.spacecodee.springbootsecurityopentemplate.data.vo.user.system.admin;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateSystemAdminVO {

    private Integer id;

    @NotBlank(message = "{system.admin.firstname.required}")
    @Size(max = 20, message = "{system.admin.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{system.admin.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{system.admin.lastname.required}")
    @Size(max = 20, message = "{system.admin.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{system.admin.lastname.pattern}")
    private String lastName;

    @Pattern(regexp = "^9\\d{8}$", message = "{system.admin.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{system.admin.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$", message = "{system.admin.profile.picture.pattern}")
    private String profilePicturePath;

    @Pattern(regexp = "^(true|false)$", message = "{system.admin.status.pattern}")
    private String status;
}
