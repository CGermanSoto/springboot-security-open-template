package com.spacecodee.springbootsecurityopentemplate.data.dto.user.details;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDetailsDTO implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String username;
    @JsonIgnore
    private String password;
    private UserDetailsRoleDTO userDetailsRoleDTO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userDetailsRoleDTO == null) return Collections.emptyList();

        var authorities = new ArrayList<>(this.userDetailsRoleDTO.getUserDetailsPermissionDTOList()
                .stream()
                .map(each -> each.getOperationDTO().getTag())
                .map(SimpleGrantedAuthority::new)
                .toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.userDetailsRoleDTO.getName()));

        return authorities;
    }

    public static UserDetailsDTO build(UserDetailsDTO user) {
        return user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}