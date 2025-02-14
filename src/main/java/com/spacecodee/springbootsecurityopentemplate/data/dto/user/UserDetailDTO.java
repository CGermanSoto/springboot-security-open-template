package com.spacecodee.springbootsecurityopentemplate.data.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserDetailDTO extends UserDTO {

    private Integer roleId;

    private String roleName;

}
