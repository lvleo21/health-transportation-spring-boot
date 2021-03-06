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
@Table(name = "TRAVELS")
public class Travel extends Auditable<String> {

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

    @NotNull(message = "{NotNull.registeredPassengers}")
    @Column(nullable = false, name = "registered_passengers")
    private Integer registeredPassengers = 0;

    @OneToMany(mappedBy = "travel")
    private List<Location> locations;

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

    public Integer getRegisteredPassengers() {
        return registeredPassengers;
    }

    public void setRegisteredPassengers(Integer registeredPassengers) {
        this.registeredPassengers = registeredPassengers;
    }

    public String getDestiny() {
        return this.destinyCity + " - " + this.destinyState;
    }
    public String getDestinyUppercase() {
        return  this.healthCenter.getPrefecture().getAddress().getCity().toUpperCase() + " - " + this.destinyCity.toUpperCase();
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public int getQntPassengers() {
        return this.getLocations().size();
    }

    public boolean itsFull(){
        return (this.getQntPassengers() == this.vehicle.getCapacity()) ? true : false;
    }
}
