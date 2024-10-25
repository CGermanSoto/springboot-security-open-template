package com.spacecodee.springbootsecurityopentemplate.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDTO(Integer id, @NotNull String username, @NotNull String password, @NotNull String fullname,
                      @NotNull String lastname, @NotNull RoleEntity roleEntity) implements Serializable {
}