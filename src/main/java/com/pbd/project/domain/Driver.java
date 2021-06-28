package com.pbd.project.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="DRIVERS")
public class Driver extends Auditable<String> {

    @NotEmpty(message = "{NotEmpty.name}")
    private String name;

    @NotNull(message = "{NotEmpty.healthCenter}")
    @ManyToOne
    @JoinColumn(name = "health_center_id", nullable = false)
    private HealthCenter healthCenter;

    @Column(name="is_active")
    private boolean active = true;

    @Column(name="is_available")
    private boolean available = true;

    @OneToMany(mappedBy = "driver")
    private List<Travel> travels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HealthCenter getHealthCenter() {
        return healthCenter;
    }

    public void setHealthCenter(HealthCenter healthCenter) {
        this.healthCenter = healthCenter;
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

    public List<Travel> getTravels() {
        return travels;
    }

    public void setTravels(List<Travel> travels) {
        this.travels = travels;
    }
}
