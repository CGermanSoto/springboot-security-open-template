package com.spacecodee.springbootsecurityopentemplate.data.vo.core.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class CreateRoleVO {

    @NotBlank(message = "{role.name.required}")
    @Size(max = 20, message = "{role.name.max.length}")
    @Pattern(regexp = "^[A-Z_]*$", message = "{role.name.pattern}")
    private String name;

    private Set<Integer> permissionIds;

}