package com.spacecodee.springbootsecurityopentemplate.data.dto.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PermissionDTO(Integer id, @NotNull RoleDTO roleDTO,
                            @NotNull OperationDTO operationDTO) implements Serializable {
}