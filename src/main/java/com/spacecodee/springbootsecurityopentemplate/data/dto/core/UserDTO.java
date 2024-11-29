package com.spacecodee.springbootsecurityopentemplate.data.dto.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDTO(Integer id, @NotNull String username, @NotNull String password, @NotNull String fullname,
                      @NotNull String lastname, @NotNull RoleDTO roleDTO) implements Serializable {
}