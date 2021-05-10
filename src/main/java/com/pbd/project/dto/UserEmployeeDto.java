package com.pbd.project.dto;

public class UserEmployeeDto {

    private String name;
    private String enrollment;
    private String username;
    private String email;
    private boolean active;

    public UserEmployeeDto(String name, String enrollment,
                           String username, String email,
                           boolean active) {
        this.name = name;
        this.enrollment = enrollment;
        this.username = username;
        this.email = email;
        this.active = active;
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
}
