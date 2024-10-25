package com.spacecodee.springbootsecurityopentemplate.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spacecodee.ticklyspace.persistence.entity.ModuleEntity;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link ModuleEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ModuleDTO(Integer id, @NotNull String moduleName,
                        @NotNull String moduleBasePath) implements Serializable {
}