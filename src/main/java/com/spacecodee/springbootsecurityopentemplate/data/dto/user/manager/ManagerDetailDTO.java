package com.spacecodee.springbootsecurityopentemplate.data.dto.user.manager;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ManagerDetailDTO extends ManagerDTO {

    private Integer roleId;

    private String roleName;

}
