package com.huypq.web.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huypq.web.admin.config.AuthConstants;
import com.huypq.web.admin.logging.ObjectName;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Objects;

@Entity
@Data
@Table(name = "user")
@ObjectName(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Pattern(regexp = AuthConstants.LOGIN_REGEX)
    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @NotNull
    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email
    @NotNull
    @Column(name = "email", nullable = false, length = 254, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false, name = "is_active")
    private boolean activated = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "authority")
    private Authority authority;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "date_of_birth")
    private Timestamp dob;

    @Column(name = "sex")
    private Integer sex;

    @NotNull
    @Column(name = "login_fail_count", nullable = false)
    private Long loginFailCount;

    @Column(name = "account_lock_until_datetime")
    private Timestamp accountLockUntilDateTime;

    @Column(name = "info_update_datetime")
    private Timestamp infoUpdateDateTime;

    @Column(name = "phone_number")
    private String phoneNumber;

    public User() {
    }

    // Lowercase the login before saving it in database
    public void setUsername(String username) {
        this.username = StringUtils.lowerCase(username, Locale.ENGLISH);
    }

    public String getFullName() {
        return ((Objects.isNull(this.firstName) ? "" : this.firstName) + " " + (Objects.isNull(this.lastName) ? "" : this.lastName)).trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return activated == user.activated && Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(department, user.department) && Objects.equals(dob, user.dob) && Objects.equals(sex, user.sex) && Objects.equals(loginFailCount, user.loginFailCount) && Objects.equals(accountLockUntilDateTime, user.accountLockUntilDateTime) && Objects.equals(infoUpdateDateTime, user.infoUpdateDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, email, activated, department, dob, sex, loginFailCount, accountLockUntilDateTime, infoUpdateDateTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(Long loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public Timestamp getAccountLockUntilDateTime() {
        return accountLockUntilDateTime;
    }

    public void setAccountLockUntilDateTime(Timestamp accountLockUntilDateTime) {
        this.accountLockUntilDateTime = accountLockUntilDateTime;
    }

    public Timestamp getInfoUpdateDateTime() {
        return infoUpdateDateTime;
    }

    public void setInfoUpdateDateTime(Timestamp infoUpdateDateTime) {
        this.infoUpdateDateTime = infoUpdateDateTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
