package com.spacecodee.springbootsecurityopentemplate.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OperationDTO(Integer id, @NotNull String tag, @NotNull String path, @NotNull String httpMethod,
                           @NotNull Boolean permitAll, @NotNull Integer moduleId) implements Serializable {
}