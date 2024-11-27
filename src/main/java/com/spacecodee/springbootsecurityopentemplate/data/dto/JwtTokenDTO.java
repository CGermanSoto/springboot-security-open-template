package com.spacecodee.springbootsecurityopentemplate.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spacecodee.springbootsecurityopentemplate.data.base.IJwtTokenFields;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JwtTokenDTO(Integer id, @NotNull String token, @NotNull Boolean isValid,
                          @NotNull Date expiryDate) implements IJwtTokenFields, Serializable {
    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public Date getExpiryDate() {
        return this.expiryDate;
    }
}