package com.pbd.project.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Table(name = "EMPLOYEES")
public class Employee extends People<Long>{

    @Valid
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name ="hired_on", columnDefinition = "DATE")
    private LocalDateTime hiredOn;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name ="fired_on", columnDefinition = "DATE")
    private LocalDateTime firedOn;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getHiredOn() {
        return hiredOn;
    }

    public void setHiredOn(LocalDateTime hiredOn) {
        this.hiredOn = hiredOn;
    }

    public LocalDateTime getFiredOn() {
        return firedOn;
    }

    public void setFiredOn(LocalDateTime firedOn) {
        this.firedOn = firedOn;
    }
}
