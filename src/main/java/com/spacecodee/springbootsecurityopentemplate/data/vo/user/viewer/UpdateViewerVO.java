package com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateViewerVO {

    private Integer id;

    @NotBlank(message = "{viewer.firstname.required}")
    @Size(max = 20, message = "{viewer.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{viewer.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{viewer.lastname.required}")
    @Size(max = 20, message = "{viewer.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{viewer.lastname.pattern}")
    private String lastName;

    @Pattern(regexp = "^9\\d{8}$", message = "{viewer.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{viewer.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$",
            message = "{viewer.profile.picture.pattern}")
    private String profilePicturePath;

    @Pattern(regexp = "^(true|false)$", message = "{viewer.status.pattern}")
    private String status;
}
