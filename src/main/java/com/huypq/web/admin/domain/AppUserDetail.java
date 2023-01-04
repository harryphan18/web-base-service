package com.huypq.web.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huypq.web.admin.security.AuthoritiesConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AppUserDetail implements UserDetails {
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String fullName;
    private String email;
    private String role;
    private Boolean isRootOrAdmin = false;
    private Set<String> permissions = new LinkedHashSet<>();

    public AppUserDetail(final User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullName = (user.getFullName().trim());
        this.email = user.getEmail();
        this.role = user.getAuthority().getName();
        this.isRootOrAdmin =  AuthoritiesConstants.ROOT_ADMIN.equals(user.getAuthority().getName()) || AuthoritiesConstants.ADMIN.equals(user.getAuthority().getName());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (permissions == null || permissions.isEmpty()) {
            return Collections.singleton(new SimpleGrantedAuthority(this.role));
        }
        Set<GrantedAuthority> allRoleAndPermission = new LinkedHashSet<>();
        allRoleAndPermission.add(new SimpleGrantedAuthority(this.role));
        allRoleAndPermission.addAll(permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        return allRoleAndPermission;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getIsRootOrAdmin() {
        return isRootOrAdmin;
    }

    public void setRootOrAdmin(Boolean rootOrAdmin) {
        isRootOrAdmin = rootOrAdmin;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
