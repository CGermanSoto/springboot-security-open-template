package com.spacecodee.springbootsecurityopentemplate.data.dto.user.details;

import com.spacecodee.ticklyspace.enums.RoleEnum;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDetailsRoleDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private RoleEnum name;
    private List<UserDetailsPermissionDTO> userDetailsPermissionDTOList;
}
