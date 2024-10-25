package com.spacecodee.springbootsecurityopentemplate.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.RoleEntity;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PermissionDTO(Integer id, @NotNull RoleEntity roleEntity,
                            @NotNull OperationEntityDto operationEntity) implements Serializable {
}