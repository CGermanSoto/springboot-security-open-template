package com.spacecodee.springbootsecurityopentemplate.data.dto.core.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RoleDetailDTO extends RoleDTO {

    private Set<String> permissions;

}