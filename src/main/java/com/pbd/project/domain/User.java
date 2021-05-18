package com.pbd.project.domain;


import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User extends AbstractEntity<Long> {


    @Column(nullable = false, unique = true)
    @Length(min = 5, message = "{Length.user.username}")
    @NotEmpty(message = "{NotEmpty.user.username}")
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    @Email(message = "{Email.error}")
    @NotEmpty(message = "{NotEmpty.email}")
    private String email;

    @Column(name = "is_active")
    private Boolean active = true;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "created_at", columnDefinition = "DATE")
    private LocalDate createdAt;

    @Column(length = 7, nullable = true)
    private String enrollment;

    @NotNull
    @NotEmpty(message = "{NotNull.name}")
    private String name;

    @ManyToOne // um centro de saúde poderá ter muitos users
    @JoinColumn(name = "health_center_id", nullable = true) // nome da chave estrangeira na tabela
    private HealthCenter healthCenter;

    @Column(name = "is_staff")
    private Boolean staff = false;

    @OneToMany(mappedBy="userWhoRequested")
    private List<OrderResetPassword> listOfUserWhoRequested;

    @OneToMany(mappedBy="userWhoSolvedOrder")
    private List<OrderResetPassword> listfOfOrderResetPasswords;




    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public String getName() {
        return name;
    }

    public HealthCenter getHealthCenter() {
        return healthCenter;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealthCenter(HealthCenter healthCenter) {
        this.healthCenter = healthCenter;
    }

    @Override
    public String toString() {
        return "User{\n" +
                ", id='" + this.getId() + '\n' +
                ", username='" + username + '\n' +
                ", password='" + password + '\n' +
                ", email='" + email + '\n' +
                ", active=" + active + '\n' +
                ", roles=" + roles + '\n' +
                ", createdAt=" + createdAt + '\n' +
                ", enrollment='" + enrollment + '\n' +
                ", name='" + name + '\n' +
                ", healthCenter=" + healthCenter + '\n' +
                ", staff=" + staff + '\n' +
                '}';
    }

    public String ShowRoles() {
        String text = "";

        for (Role role : this.roles) {
            text += role.getRole() + "<br>";
        }

        return text;
    }

    public Boolean getStaff() {
        return staff;
    }

    public void setStaff(Boolean staff) {
        this.staff = staff;
    }


}
