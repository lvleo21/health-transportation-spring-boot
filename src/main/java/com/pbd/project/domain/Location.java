package com.pbd.project.domain;

import com.pbd.project.domain.enums.PassengerCategory;
import com.pbd.project.domain.enums.PassengerTransition;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "LOCATIONS")
public class Location extends Auditable<String> {

    @ManyToOne
    @NotNull(message = "{NotEmpty.passenger}")
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne
    @NotNull(message = "{NotEmpty.travel}")
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    @NotNull(message = "{NotEmpty.travel.category}")
    @Column(length = 13)
    @Enumerated(EnumType.STRING)
    private PassengerCategory category;

    @NotNull(message = "{NotEmpty.travel.transition}")
    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private PassengerTransition transition;

    @Column(name="is_preferential")
    private boolean preferential = false;

    @NotEmpty(message = "{NotEmpty.destinationHospital}")
    @Column(name="destination_hospital", nullable = false)
    private String destinationHospital;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name ="created_at", columnDefinition = "DATE", nullable = false)
    private LocalDate createdAt = LocalDate.now();


    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public PassengerCategory getCategory() {
        return category;
    }

    public void setCategory(PassengerCategory category) {
        this.category = category;
    }

    public PassengerTransition getTransition() {
        return transition;
    }

    public void setTransition(PassengerTransition transition) {
        this.transition = transition;
    }

    public boolean isPreferential() {
        return preferential;
    }

    public void setPreferential(boolean preferential) {
        this.preferential = preferential;
    }

    public String getDestinationHospital() {
        return destinationHospital;
    }

    public void setDestinationHospital(String destinationHospital) {
        this.destinationHospital = destinationHospital;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


}
