package com.pbd.project.dto;

import com.pbd.project.domain.User;

import javax.persistence.Column;

public class Employee {

    private Long id;
    private String name;
    private String enrollment;
    private String username;
    private String email;
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", enrollment='" + enrollment + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }

    public void toMe(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.enrollment = user.getEnrollment();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.active = user.getActive();
    }
}
