package com.spacecodee.springbootsecurityopentemplate.data.vo.user.viewer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViewerFilterVO {

    @Pattern(regexp = "^\\w*$", message = "{viewer.filter.username.pattern}")
    private String username;

    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{viewer.filter.name.pattern}")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{viewer.filter.lastname.pattern}")
    private String lastName;

    @Min(value = 1, message = "{viewer.filter.role.id.min}")
    private Integer roleId;

    @Pattern(regexp = "^(true|false)$", message = "{viewer.filter.enabled.pattern}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String enabled;

    @Min(value = 0, message = "{viewer.page.min}")
    private Integer page = 0;

    @Min(value = 1, message = "{viewer.size.min}")
    @Max(value = 100, message = "{viewer.size.max}")
    private Integer size = 10;

    @Pattern(regexp = "^(username|firstName|lastName)$", message = "{viewer.sort.invalid}")
    private String sortBy = "username";

    @Pattern(regexp = "^(ASC|DESC)$", message = "{viewer.sort.direction.invalid}")
    private String sortDirection = "ASC";

    public Boolean getEnabled() {
        return enabled != null ? Boolean.parseBoolean(enabled) : null;
    }
}