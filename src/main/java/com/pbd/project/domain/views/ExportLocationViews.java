package com.pbd.project.domain.views;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Immutable
@Table(name="ExportLocationsView")
public class ExportLocationViews implements Serializable {
    @Id
    @Column(name="travel_id")
    private Long travel_id;

    @Column(name="name")
    public String name;

    @Column(name="category")
    public String category;

    @Column(name="rg")
    public String rg;

    @Column(name="sus")
    public String sus;

    @Column(name="date_of_birth")
    public String date_of_birth;

    @Column(name="address")
    public String address;

    @Column(name="transition")
    public String transition;

    @Column(name="destination_hospital")
    public String destination_hospital;

    @Column(name="cell_phone")
    public String cell_phone;


    public Long getTravel_id() {
        return travel_id;
    }

    public void setTravel_id(Long travel_id) {
        this.travel_id = travel_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSus() {
        return sus;
    }

    public void setSus(String sus) {
        this.sus = sus;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }

    public String getDestination_hospital() {
        return destination_hospital;
    }

    public void setDestination_hospital(String destination_hospital) {
        this.destination_hospital = destination_hospital;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    @Override
    public String toString() {
        return "[id="+this.travel_id+"]";
    }
}
