package com.spacecodee.springbootsecurityopentemplate.data.vo.jwt;

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
public class JwtTokeUVO implements Serializable {
    private Integer id;
    @NotNull
    private String token;
    @NotNull
    private Boolean isValid;
    @NotNull
    private Instant expiryDate;
    @NotNull
    private UserEntity userEntity;
}