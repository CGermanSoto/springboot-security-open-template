package com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOperationVO {
    @NotBlank(message = "{operation.tag.required}")
    @Size(max = 100, message = "{operation.tag.max.length}")
    private String tag;

    @Size(max = 50, message = "{operation.path.max.length}")
    private String path = "";

    @NotBlank(message = "{operation.http.method.required}")
    @Size(max = 10, message = "{operation.http.method.max.length}")
    @Pattern(regexp = "^(GET|POST|PUT|DELETE|PATCH|HEAD|OPTIONS)$",
            message = "{operation.http.method.pattern}")
    private String httpMethod;

    @Pattern(regexp = "^(true|false)$", message = "{operation.permit.all.pattern}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String permitAll = "false";

    @NotNull(message = "{operation.module.required}")
    @Min(value = 1, message = "{operation.filter.module.id.min}")
    private Integer moduleId;

    public Boolean getPermitAll() {
        return Boolean.parseBoolean(permitAll);
    }

    public void setPermitAll(Boolean permitAll) {
        this.permitAll = permitAll != null ? permitAll.toString() : "false";
    }
}