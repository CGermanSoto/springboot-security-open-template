package com.spacecodee.springbootsecurityopentemplate.data.dto.user.guest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GuestDetailDTO extends GuestDTO {

    private Integer roleId;

    private String roleName;

}
