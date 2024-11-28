// DeveloperDTO.java
package com.spacecodee.springbootsecurityopentemplate.data.dto.user;

import com.spacecodee.springbootsecurityopentemplate.data.dto.RoleDTO;

public record DeveloperDTO(
        Integer id,
        String username,
        String fullname,
        String lastname,
        RoleDTO roleDTO) {
}