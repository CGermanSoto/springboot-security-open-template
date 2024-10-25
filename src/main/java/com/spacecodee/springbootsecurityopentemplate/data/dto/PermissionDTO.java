package com.spacecodee.springbootsecurityopentemplate.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spacecodee.ticklyspace.persistence.entity.RoleEntity;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link com.spacecodee.ticklyspace.persistence.entity.PermissionEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PermissionDTO(Integer id, @NotNull RoleEntity roleEntity,
                            @NotNull OperationEntityDto operationEntity) implements Serializable {
}