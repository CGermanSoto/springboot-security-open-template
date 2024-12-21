package com.spacecodee.springbootsecurityopentemplate.data.vo.auth.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spacecodee.springbootsecurityopentemplate.data.base.IJwtTokenFields;
import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtTokenUVO implements IJwtTokenFields, Serializable {
    @Getter
    private Integer id;

    @NotNull(message = "{validation.jwt.token.required," +
            "${validatedValue}")
    private String token;

    @Getter
    @NotNull(message = "{validation.jwt.valid.required," +
            "${validatedValue}")
    private Boolean valid;

    @NotNull(message = "{validation.jwt.expiry.required," +
            "${validatedValue}")
    private Date expiryDate;

    @Getter
    @NotNull(message = "{validation.jwt.user.required," +
            "${validatedValue}")
    private UserEntity userEntity;

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public Date getExpiryDate() {
        return this.expiryDate;
    }
}