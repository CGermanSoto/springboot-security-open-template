package com.spacecodee.springbootsecurityopentemplate.data.vo.core.operation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperationFilterVO {

    @Pattern(regexp = "^[a-zA-Z_]*$", message = "{operation.filter.tag.pattern}")
    private String tag;

    @Pattern(regexp = "^$|^/[a-zA-Z-]+(?:/[a-zA-Z-]+)*+$", message = "{operation.filter.path.pattern}")
    private String path;

    @Pattern(regexp = "^(GET|POST|PUT|DELETE|PATCH|HEAD|OPTIONS)$", message = "{operation.http.method.pattern}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String httpMethod;

    @Pattern(regexp = "^(true|false)$", message = "{operation.filter.permit.all.pattern}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String permitAll;

    @Min(value = 1, message = "{operation.filter.module.id.min}")
    private Integer moduleId;

    @Min(value = 0, message = "{operation.page.min}")
    private Integer page = 0;

    @Min(value = 1, message = "{operation.size.min}")
    @Max(value = 100, message = "{operation.size.max}")
    private Integer size = 10;

    @Pattern(regexp = "^(tag|path|httpMethod)$", message = "{operation.sort.invalid}")
    private String sortBy = "tag";

    @Pattern(regexp = "^(ASC|DESC)$", message = "{operation.sort.direction.invalid}")
    private String sortDirection = "ASC";

    public Boolean getPermitAll() {
        return permitAll != null ? Boolean.parseBoolean(permitAll) : null;
    }

    public void setPermitAll(Boolean permitAll) {
        this.permitAll = permitAll != null ? permitAll.toString() : null;
    }
}