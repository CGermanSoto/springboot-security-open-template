package com.spacecodee.springbootsecurityopentemplate.data.vo.user.editor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditorFilterVO {

    @Pattern(regexp = "^\\w*$", message = "{editor.filter.username.pattern}")
    private String username;

    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{editor.filter.name.pattern}")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{editor.filter.lastname.pattern}")
    private String lastName;

    @Pattern(regexp = "^(true|false)$", message = "{editor.filter.enabled.pattern}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String enabled;

    @Min(value = 0, message = "{editor.page.min}")
    private Integer page = 0;

    @Min(value = 1, message = "{editor.size.min}")
    @Max(value = 100, message = "{editor.size.max}")
    private Integer size = 10;

    @Pattern(regexp = "^(username|firstName|lastName)$", message = "{editor.sort.invalid}")
    private String sortBy = "username";

    @Pattern(regexp = "^(ASC|DESC)$", message = "{editor.sort.direction.invalid}")
    private String sortDirection = "ASC";

    public Boolean getEnabled() {
        return enabled != null ? Boolean.parseBoolean(enabled) : null;
    }
}
