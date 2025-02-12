package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import com.spacecodee.springbootsecurityopentemplate.enums.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RoleSecurityDTO {

    private int id;

    private RoleEnum name;

    private List<PermissionSecurityDTO> permissionDTOList;

}
