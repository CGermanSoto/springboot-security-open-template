package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDetailsOperationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String tag;
    private String path;
    private String httpMethod;
    private boolean permitAll;
    private UserDetailsModuleDTO moduleDTO;

}
