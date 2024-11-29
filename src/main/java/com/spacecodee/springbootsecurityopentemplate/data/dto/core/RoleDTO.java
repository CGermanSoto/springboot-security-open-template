package com.spacecodee.springbootsecurityopentemplate.data.dto.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RoleDTO(Integer id, @NotNull String name) implements Serializable {
}