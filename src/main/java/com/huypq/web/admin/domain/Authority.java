package com.huypq.web.admin.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@Table(name = "authority")
public class Authority implements Serializable {
    @NotNull
    @Id
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(length = 50, name = "display_name")
    private String displayName;

    @NotNull
    @Column(name = "pos_index")
    private Integer posIndex;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        return Objects.equals(name, ((Authority) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public static class AuthorityName{

        private AuthorityName(){}

        public static final String ROOT_ADMIN = "ROLE_ROOT_ADMIN";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String STAFF = "ROLE_USER";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getPosIndex() {
        return posIndex;
    }

    public void setPosIndex(Integer posIndex) {
        this.posIndex = posIndex;
    }
}
