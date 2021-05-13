package com.pbd.project.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name="HEALTH_CENTERS")
public class HealthCenter extends AbstractEntity<Long>{

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Valid
    @OneToOne
    @JoinColumn(name = "prefecture_id")
    private Prefecture prefecture;

    @NotEmpty(message="{NotEmpty.cnpj}")
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(name="is_active")
    private boolean active;

    @OneToMany(mappedBy = "healthCenter")
    private List<User> users;

    @OneToMany(mappedBy = "healthCenter")
    private List<Passenger> passengers;

    @OneToMany(mappedBy = "healthCenter")
    private List<Driver> drivers;

    @OneToMany(mappedBy = "healthCenter")
    private List<Vehicle> vehicles;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Prefecture getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(Prefecture prefecture) {
        this.prefecture = prefecture;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getAdressInformation(){
        String str = "Rua: " + this.address.getPublicPlace()  + "; ";
        str +=  "Bairro: " + this.address.getNeighborhood()  + "; ";
        str +=  "Nª: " + this.address.getNumber()  + "; ";
        str +=  "Complemento: " + this.address.getComplement() + "; ";

        return str;

    }
}
