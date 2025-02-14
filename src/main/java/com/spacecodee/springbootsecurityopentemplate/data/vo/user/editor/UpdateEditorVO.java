package com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateEditorVO {

    private Integer id;

    @NotBlank(message = "{editor.firstname.required}")
    @Size(max = 20, message = "{editor.firstname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{editor.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{editor.lastname.required}")
    @Size(max = 20, message = "{editor.lastname.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{editor.lastname.pattern}")
    private String lastName;

    @Pattern(regexp = "^9\\d{8}$", message = "{editor.phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{editor.profile.picture.size}")
    @Pattern(regexp = "^https?://[\\w.-]+\\.\\w{2,6}/[\\w/-]+\\.(jpg|jpeg|png)$",
            message = "{editor.profile.picture.pattern}")
    private String profilePicturePath;

    @Pattern(regexp = "^(true|false)$", message = "{editor.status.pattern}")
    private String status;
}
