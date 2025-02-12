package com.spacecodee.springbootsecurityopentemplate.data.dto.user.developer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeveloperDTO {

    private Integer id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String profilePicturePath;

    private Boolean status;

}