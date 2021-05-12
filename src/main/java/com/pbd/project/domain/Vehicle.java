package com.pbd.project.domain;


import com.pbd.project.domain.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "VEHICLES")
public class Vehicle extends AbstractEntity<Long>{

    @NotEmpty(message = "{NotEmpty.name}")
    private String name;

    @NotEmpty(message = "{NotEmpty.capacity}")
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "health_center_id", nullable = false)
    private HealthCenter healthCenter;

    @NotEmpty(message = "{NotEmpty.plaque}")
    private String plaque;

    @Column(name="is_active")
    private boolean active = true;

    @Column(name="is_available")
    private boolean available = true;

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
}
