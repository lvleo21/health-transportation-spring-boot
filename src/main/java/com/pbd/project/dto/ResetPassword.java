package com.pbd.project.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ResetPassword {

    @Email
    @NotBlank(message = "{NotEmpty.email}")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
