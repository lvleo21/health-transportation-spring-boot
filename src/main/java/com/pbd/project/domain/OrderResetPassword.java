package com.pbd.project.domain;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="ORDER_RESET_PASSWORDS")
public class OrderResetPassword extends Auditable<String> {

    @ManyToOne
    @JoinColumn(name="id_user_who_requested")//! ID do usuário que solicitou o reset;
    private User userWhoRequested; //! Usuário que fez solicitação;

    @ManyToOne
    @JoinColumn(name="id_user_who_solved_order", nullable = true) //! ID do usuário que resolveu o reset;
    private User userWhoSolvedOrder; //! Usuário que fez o reset;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "request_on", columnDefinition = "DATE")
    private LocalDate requestOn = LocalDate.now(); //! Data da solicitação;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "completed_in", columnDefinition = "DATE", nullable = true)
    private LocalDate completedIn; //! Data em que a solicitação foi concluída;

    @Column(name="was_rejected") //! Foi rejeitado ?
    private boolean wasRejected = false; //! Se a solicitação foi rejeitada ou não;

    public User getUserWhoRequested() {
        return userWhoRequested;
    }

    public void setUserWhoRequested(User userWhoRequested) {
        this.userWhoRequested = userWhoRequested;
    }

    public User getUserWhoSolvedOrder() {
        return userWhoSolvedOrder;
    }

    public void setUserWhoSolvedOrder(User userWhoSolvedOrder) {
        this.userWhoSolvedOrder = userWhoSolvedOrder;
    }

    public LocalDate getRequestOn() {
        return requestOn;
    }

    public void setRequestOn(LocalDate requestOn) {
        this.requestOn = requestOn;
    }

    public LocalDate getCompletedIn() {
        return completedIn;
    }

    public void setCompletedIn(LocalDate completedIn) {
        this.completedIn = completedIn;
    }

    public boolean isWasRejected() {
        return wasRejected;
    }

    public void setWasRejected(boolean wasRejected) {
        this.wasRejected = wasRejected;
    }

}
