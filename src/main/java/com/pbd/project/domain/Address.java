package com.pbd.project.domain;

import com.pbd.project.domain.enums.UF;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ADRESSES")
public class Address extends AbstractEntity<Long> {

    @NotBlank
    @NotNull(message = "{NotNull.address.publicPlace}")
    @Column(name = "public_place")
    private String publicPlace;

    @NotBlank
    @NotNull(message = "{NotNull.address.neighborhood}")
    private String neighborhood;

    @NotNull(message = "{NotNull.address.number}")
    @Digits(integer = 5, fraction = 0)
    @Column(length = 5)
    private Integer number;

    private String complement;

    @NotBlank
    @NotNull(message = "{NotNull.address.city}")
    private String city;

    @NotNull(message = "{NotNull.address.state}")
    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private UF State;

    @NotBlank
    @NotNull(message = "{NotNull.address.country}")
    private String country;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Prefecture prefecture;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private HealthCenter healthCenter;

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UF getState() {
        return State;
    }

    public void setState(UF state) {
        State = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
