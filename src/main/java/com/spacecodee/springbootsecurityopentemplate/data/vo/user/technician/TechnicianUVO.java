package com.spacecodee.springbootsecurityopentemplate.data.vo.user.technician;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TechnicianUVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;

    @NotBlank
    @NotEmpty
    @Size(min = 3)
    private String username;

    @NotBlank
    @NotEmpty
    @Size(min = 1)
    private String fullname;

    @NotBlank
    @NotEmpty
    @Size(min = 1)
    private String lastname;
}