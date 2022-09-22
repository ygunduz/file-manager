package com.ets.filemanager.security;

import com.ets.filemanager.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class EtsUserDetails implements UserDetails {
    private final Long id;
    private final String password;
    private final String username;

    private final Boolean enabled;

    private EtsUserDetails(
            Long id,
            String username,
            String password,
            Boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public static UserDetails fromUser(User user) {
        return new EtsUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEnabled());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }
}
