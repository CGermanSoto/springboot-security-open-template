package com.spacecodee.springbootsecurityopentemplate.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.spacecodee.ticklyspace.persistence.entity.JwtTokenEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record JwtTokenDTO(Integer id, @NotNull String token, @NotNull Boolean isValid,
                          @NotNull Instant expiryDate) implements Serializable {
}