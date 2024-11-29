package com.spacecodee.springbootsecurityopentemplate.data.vo.auth.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtTokeAVO implements Serializable {
    @NotNull(message = "{validation.field.required}")
    private String token;

    @NotNull(message = "{validation.field.required}")
    private Boolean isValid;

    @NotNull(message = "{validation.field.required}")
    private Instant expiryDate;

    @NotNull(message = "{validation.field.required}")
    private UserEntity userEntity;
}