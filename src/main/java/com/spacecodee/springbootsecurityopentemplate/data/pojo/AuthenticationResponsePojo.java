package com.spacecodee.springbootsecurityopentemplate.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AuthenticationResponsePojo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String jwt;
}