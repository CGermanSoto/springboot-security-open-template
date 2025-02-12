package com.spacecodee.springbootsecurityopentemplate.data.dto.user;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserProfileDTO {

    private Integer id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String profilePicturePath;

    private Boolean status;

    private RoleEnum role;

}