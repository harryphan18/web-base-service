package com.huypq.web.admin.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenPrincipal implements Serializable {
    private Long userId;
    private String username;
    private Boolean isRootOrAdmin;
    private Integer domainId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getRootOrAdmin() {
        return isRootOrAdmin;
    }

    public void setRootOrAdmin(Boolean rootOrAdmin) {
        isRootOrAdmin = rootOrAdmin;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }
}