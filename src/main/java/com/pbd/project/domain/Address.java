package com.pbd.project.domain;

import com.pbd.project.domain.enums.UF;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "ADRESSES")
public class Address extends Auditable<String> {


    @NotBlank(message = "{notEmpty.publicPlace}")
    @Column(name = "public_place")
    private String publicPlace;

    @NotBlank(message = "{notEmpty.neighborhood}")
    private String neighborhood;

    @NotNull(message = "{notNull.number}")
    @Digits(integer = 5, fraction = 0)
    @Column(length = 5)
    private Integer number;

    private String complement;

    @NotBlank(message = "{notEmpty.city}")
    private String city;

    @NotNull(message = "{notEmpty.state}")
    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private UF state;

    @NotBlank(message = "{notEmpty.country}")
    private String country;


    @OneToMany(mappedBy = "address")
    private List<Passenger> passengers;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UF getState() {
        return state;
    }

    public void setState(UF state) {
        this.state = state;
    }

    public String formatedAddress(){
        return this.city + " - " + this.state;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getAdressInformation(){
        StringBuilder builder = new StringBuilder();

        builder.append("<b>Rua:</b> " + this.getPublicPlace() + "</br>");
        builder.append("<b>Bairro:</b> " + this.getNeighborhood() + "</br>");
        builder.append("<b>Nº:</b> " + this.getNumber() + "</br>");
        builder.append("<b>Complemento:</b> " + this.getComplement() + "</br>");
        builder.append("<b>Cidade:</b> " + this.getCity() + "</br>");
        builder.append("<b>Estado:</b> " + this.getState());

        return builder.toString();

    }

    public String getAddressToPdf(){
        return this.publicPlace + ", "  + this.number + ", " + this.neighborhood;
    }
}
