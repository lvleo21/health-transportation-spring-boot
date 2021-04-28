package com.pbd.project.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name="USERS")
public class User extends AbstractEntity<Long> {

    @Column(nullable = false, unique = true)
    @Length(min = 5, message = "{Length.users.username}")
    @NotEmpty(message = "{NotEmpty.user.username}")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "{NotEmpty.user.password}")
    private String password;

    @Column(nullable = false, unique = true)
    @Email(message = "{Email.user.error}")
    @NotEmpty(message = "{NotEmpty.user.email}")
    private String email;

    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
