package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleSecurityDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;

    private RoleEnum name;

    private List<PermissionSecurityDTO> permissionSecurityDTOList;

}
