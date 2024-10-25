package com.spacecodee.springbootsecurityopentemplate.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JwtTokenDTO(Integer id, @NotNull String token, @NotNull Boolean isValid,
                          @NotNull Instant expiryDate) implements Serializable {
}