package com.spacecodee.springbootsecurityopentemplate.data.vo.user.manager;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManagerFilterVO {

    @Pattern(regexp = "^\\w*$", message = "{manager.filter.username.pattern}")
    private String username;

    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{manager.filter.name.pattern}")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{manager.filter.lastname.pattern}")
    private String lastName;

    @Pattern(regexp = "^(true|false)$", message = "{manager.filter.enabled.pattern}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String enabled;

    @Min(value = 0, message = "{manager.page.min}")
    private Integer page = 0;

    @Min(value = 1, message = "{manager.size.min}")
    @Max(value = 100, message = "{manager.size.max}")
    private Integer size = 10;

    @Pattern(regexp = "^(username|firstName|lastName)$", message = "{manager.sort.invalid}")
    private String sortBy = "username";

    @Pattern(regexp = "^(ASC|DESC)$", message = "{manager.sort.direction.invalid}")
    private String sortDirection = "ASC";

    public Boolean getEnabled() {
        return enabled != null ? Boolean.parseBoolean(enabled) : null;
    }
}
