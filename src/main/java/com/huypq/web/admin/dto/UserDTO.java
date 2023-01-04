package com.huypq.web.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huypq.web.admin.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank
    @Length(max = 255)
    private String login;

    @NotBlank
    @Length(max = 255)
    private String fullName;

    @Length(max = 254)
    @NotBlank
    private String email;

    private boolean isActive = false;

    private Set<String> authorities;

    @Length(max = 255)
    private String department;

    @Length(max = 255)
    private String company;

    private Integer sex;

    @NotBlank
    private String role;


    private Timestamp passwordChangeDateTime;


    private Timestamp infoUpdateDateTime;

    @JsonIgnore
    private String password;

    private Timestamp dateOfBirth;

    private Timestamp mostRecentLogin;

    private Long userType;

    private Boolean activated = Boolean.TRUE;

    private boolean hasPermission;

    @NotBlank
    @Length(min = 10, max = 10)
    private String phoneNumber;

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getUsername();
        this.fullName = (user.getFirstName() + " " + user.getLastName()).trim();
        this.email = user.getEmail();
        this.isActive = user.isActivated();
        this.authorities = Collections.singleton(user.getAuthority().getName());
        this.role = user.getAuthority().getName();
        this.dateOfBirth = user.getDob();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getPasswordChangeDateTime() {
        return passwordChangeDateTime;
    }

    public void setPasswordChangeDateTime(Timestamp passwordChangeDateTime) {
        this.passwordChangeDateTime = passwordChangeDateTime;
    }

    public Timestamp getInfoUpdateDateTime() {
        return infoUpdateDateTime;
    }

    public void setInfoUpdateDateTime(Timestamp infoUpdateDateTime) {
        this.infoUpdateDateTime = infoUpdateDateTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Timestamp getMostRecentLogin() {
        return mostRecentLogin;
    }

    public void setMostRecentLogin(Timestamp mostRecentLogin) {
        this.mostRecentLogin = mostRecentLogin;
    }

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public boolean isHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
