package com.pbd.project.domain;

import com.pbd.project.domain.enums.Gender;
import com.pbd.project.domain.enums.UF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;



@Entity
@Table(name="PASSENGERS")
public class Passenger extends AbstractEntity<Long>{

    @NotNull(message = "{NotNull.name}")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "{NotNull.people.rg}")
    @Column(nullable = false, unique = true, length = 9)
    private String rg;

    @NotNull(message = "{NotNull.people.sus}")
    @Column(nullable = false, unique = true, length = 18)
    private String sus;

    @NotNull(message = "{NotEmpty.people.dateOfBirth}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name ="date_of_birth", columnDefinition = "DATE", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull(message = "{NotNull.people.gender}")
    @Column(length = 1)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @NotNull(message = "{NotNull.people.cellPhone}")
    @Column(name="cell_phone", length = 15)// (99) 99999-9999
    private String cellPhone;

    @Column(length = 14) // (99) 9999-9999
    private String landline;

    @ManyToOne
    @NotNull(message = "{NotEmpty.healthCenter}")
    @JoinColumn(name = "health_center_id", nullable = false)
    private HealthCenter healthCenter;

    @Column(name="is_active")
    private Boolean active = true;

    @Column(name="warning_count")
    private Integer warningCount = 0;

    @Column(columnDefinition = "TEXT")
    private String observation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public HealthCenter getHealthCenter() {
        return healthCenter;
    }

    public void setHealthCenter(HealthCenter healthCenter) {
        this.healthCenter = healthCenter;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(Integer warningCount) {
        this.warningCount = warningCount;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
