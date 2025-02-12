package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsPermissionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    @JsonIgnore
    private UserDetailsRoleDTO roleDTO;
    private UserDetailsOperationDTO operationDTO;
}