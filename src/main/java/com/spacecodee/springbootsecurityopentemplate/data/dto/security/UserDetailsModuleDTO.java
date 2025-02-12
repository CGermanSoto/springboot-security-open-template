package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDetailsModuleDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String basePath;
}
