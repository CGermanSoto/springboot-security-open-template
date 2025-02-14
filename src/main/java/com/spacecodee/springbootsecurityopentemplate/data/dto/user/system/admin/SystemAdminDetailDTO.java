package com.spacecodee.springbootsecurityopentemplate.data.dto.user.system.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SystemAdminDetailDTO extends SystemAdminDTO {

    private Integer roleId;

    private String roleName;

}
