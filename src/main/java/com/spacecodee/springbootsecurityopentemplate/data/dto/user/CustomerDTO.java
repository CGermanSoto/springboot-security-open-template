package com.spacecodee.springbootsecurityopentemplate.data.dto.user;

import com.spacecodee.springbootsecurityopentemplate.data.dto.core.RoleDTO;

public record CustomerDTO(
        Integer id,
        String username,
        String fullname,
        String lastname,
        RoleDTO roleDTO) {
}