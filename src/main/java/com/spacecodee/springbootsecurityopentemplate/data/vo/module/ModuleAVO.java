package com.spacecodee.springbootsecurityopentemplate.data.vo.module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spacecodee.ticklyspace.persistence.entity.ModuleEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link ModuleEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModuleAVO implements Serializable {
    @NotNull
    private String moduleName;
    @NotNull
    @NotEmpty
    @NotBlank
    private String moduleBasePath;
}