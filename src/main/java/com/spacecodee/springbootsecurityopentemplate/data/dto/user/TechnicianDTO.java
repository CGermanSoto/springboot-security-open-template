package com.spacecodee.springbootsecurityopentemplate.data.dto.user;

import com.spacecodee.springbootsecurityopentemplate.data.dto.RoleDTO;

public record TechnicianDTO(
        Integer id,
        String username,
        String fullname,
        String lastname,
        RoleDTO roleDTO) {
}