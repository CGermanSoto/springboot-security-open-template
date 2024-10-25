package com.spacecodee.springbootsecurityopentemplate.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spacecodee.ticklyspace.persistence.entity.OperationEntity;
import com.spacecodee.ticklyspace.persistence.entity.PermissionEntity;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link OperationEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OperationEntityDto(Integer id, @NotNull String tag, @NotNull String path, @NotNull String httpMethod,
                                 @NotNull Boolean permitAll, @NotNull Integer moduleId,
                                 @NotNull Set<PermissionEntity> permissionEntities) implements Serializable {
}