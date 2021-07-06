package com.pbd.project.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "VEHICLES")
public class Vehicle extends Auditable<String>{

    @NotEmpty(message = "{NotEmpty.name}")
    private String name;

    @NotNull(message="{NotNull.capacity}")
    @Digits(integer = 2, fraction = 0)
    @Column(nullable = false, length = 2)
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "health_center_id", nullable = false)
    @NotNull(message = "{NotEmpty.healthCenter}")
    private HealthCenter healthCenter;

    @NotEmpty(message = "{NotEmpty.plaque}")
    private String plaque;

    @Column(name="is_active")
    private boolean active = true;

    @Column(name="is_available")
    private boolean available = true;

    public List<Travel> getTravels() {
        return travels;
    }

    public void setTravels(List<Travel> travels) {
        this.travels = travels;
    }

    @OneToMany(mappedBy = "vehicle")
    private List<Travel> travels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public HealthCenter getHealthCenter() {
        return healthCenter;
    }

    public void setHealthCenter(HealthCenter healthCenter) {
        this.healthCenter = healthCenter;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getFormatedString(){
        return "[" + this.getPlaque() + "] " + this.getName() + " - " + this.getCapacity();
    }

    public boolean canDelete(){
        return (this.getTravels().size() == 0) ? true : false;
    }

}
