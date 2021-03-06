package com.pbd.project.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "PREFECTURES")
public class Prefecture extends Auditable<String> {

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(nullable = false, unique = true)
    @Email(message = "{Email.error}")
    @NotEmpty(message = "{NotEmpty.email}")
    private String email;

    @NotEmpty(message = "{NotEmpty.prefecture.site}")
    @Column(nullable = false, unique = true)
    private String site;

    @NotEmpty(message = "{NotEmpty.cnpj}")
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    private boolean active;

    @OneToOne(mappedBy = "prefecture")
    private HealthCenter healthCenter;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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

    public HealthCenter getHealthCenter() {
        return healthCenter;
    }

    public void setHealthCenter(HealthCenter healthCenter) {
        this.healthCenter = healthCenter;
    }

    public String getCityAndState(){
        return this.address.getCity() + " - " + this.address.getState();
    }

    public String getFormatedAddress(){
        // Ex.: Praça Monsenhor Alfredo de Arruda Câmara, 205, Centro
        return this.address.getPublicPlace() + ", " + this.address.getNumber() + ", " + this.getAddress().getNeighborhood();
    }

    public String getFormatedInfo(){
        // Ex.: Afogados da Ingazeira - PE | CNPJ: 123.123.12/1232.4
        return this.getCityAndState() + " | " + "CNPJ: " + this.getCnpj();
    }
}
