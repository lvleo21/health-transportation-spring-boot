package com.pbd.project.domain;

import com.pbd.project.domain.enums.TravelStatus;
import com.pbd.project.domain.enums.UF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="TRAVELS")
public class Travel extends AbstractEntity<Long>{

    @NotNull(message = "{NotEmpty.departureDate}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "departure_date", columnDefinition = "DATE", nullable = false)
    private LocalDate departureDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "return_date", columnDefinition = "DATE")
    private LocalDate returnDate;

    @NotBlank(message = "{notEmpty.city}")
    private String destinyCity;

    @NotNull(message = "{notEmpty.state}")
    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private UF destinyState;

    @ManyToOne
    @NotNull(message = "{NotEmpty.healthCenter}")
    @JoinColumn(name = "health_center_id", nullable = false)
    private HealthCenter healthCenter;

    @ManyToOne
    @NotNull(message = "{NotEmpty.driver}")
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne
    @NotNull(message = "{NotEmpty.vehicle}")
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotNull(message = "{notEmpty.status}")
    @Column(length = 15)
    @Enumerated(EnumType.STRING)
    private TravelStatus status = TravelStatus.AGUARDANDO;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @Column(name="is_active")
    private boolean active = true;

    @NotNull(message="{NotNull.registeredPassengers}")
    @Column(nullable = false, name = "registered_passengers")
    private Integer registeredPassengers = 0;

    @OneToMany(mappedBy = "travel")
    private List<Location> locações;

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getDestinyCity() {
        return destinyCity;
    }

    public void setDestinyCity(String destinyCity) {
        this.destinyCity = destinyCity;
    }

    public UF getDestinyState() {
        return destinyState;
    }

    public void setDestinyState(UF destinyState) {
        this.destinyState = destinyState;
    }

    public HealthCenter getHealthCenter() {
        return healthCenter;
    }

    public void setHealthCenter(HealthCenter healthCenter) {
        this.healthCenter = healthCenter;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public TravelStatus getStatus() {
        return status;
    }

    public void setStatus(TravelStatus status) {
        this.status = status;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getRegisteredPassengers() {
        return registeredPassengers;
    }

    public void setRegisteredPassengers(Integer registeredPassengers) {
        this.registeredPassengers = registeredPassengers;
    }

    public List<Location> getLocações() {
        return locações;
    }

    public void setLocações(List<Location> locações) {
        this.locações = locações;
    }
}
