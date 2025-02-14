package com.spacecodee.springbootsecurityopentemplate.data.dto.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


@Data
@NoArgsConstructor
public class UserSecurityDTO implements UserDetails {

    private long id;

    private String name;

    private String username;

    @JsonIgnore
    private String password;

    private transient RoleSecurityDTO roleSecurityDTO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roleSecurityDTO == null)
            return Collections.emptyList();

        return new ArrayList<>(this.roleSecurityDTO.getPermissionDTOList()
                .stream()
                .map(each -> each.getOperationDTO().getTag())
                .map(SimpleGrantedAuthority::new)
                .toList());
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}