package com.spacecodee.springbootsecurityopentemplate.data.vo.user.guest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GuestFilterVO {

    @Pattern(regexp = "^\\w*$", message = "{guest.filter.username.pattern}")
    private String username;

    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{guest.filter.name.pattern}")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{guest.filter.lastname.pattern}")
    private String lastName;

    @Pattern(regexp = "^(true|false)$", message = "{guest.filter.enabled.pattern}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String enabled;

    @Min(value = 0, message = "{guest.page.min}")
    private Integer page = 0;

    @Min(value = 1, message = "{guest.size.min}")
    @Max(value = 100, message = "{guest.size.max}")
    private Integer size = 10;

    @Pattern(regexp = "^(username|fullName|lastName)$", message = "{guest.sort.invalid}")
    private String sortBy = "username";

    @Pattern(regexp = "^(ASC|DESC)$", message = "{guest.sort.direction.invalid}")
    private String sortDirection = "ASC";

    public Boolean getEnabled() {
        return enabled != null ? Boolean.parseBoolean(enabled) : null;
    }
}
