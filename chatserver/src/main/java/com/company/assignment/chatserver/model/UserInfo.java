package com.company.assignment.chatserver.model;

import com.company.assignment.chatserver.auth.entity.PrivilegeEntity;
import com.company.assignment.chatserver.auth.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfo implements UserDetails {
    private String username;
    private Long userId;
    private String password;

    private String fullName;

    private List<GrantedAuthority> authorities;

    public UserInfo(UserEntity userEntity) {
        username = userEntity.getEmail();
        fullName = userEntity.getFullName();
        userId = userEntity.getUserId();
        password = userEntity.getPassword();

        authorities =userEntity.getRoles()
                .stream()
                .flatMap(role-> role.getPrivileges().stream())
                .map(PrivilegeEntity::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public String getFullName() {
        return fullName;
    }
}
