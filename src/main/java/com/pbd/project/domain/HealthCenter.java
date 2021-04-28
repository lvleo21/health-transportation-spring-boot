package com.pbd.project.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="HEALTH_CENTERS")
public class HealthCenter extends AbstractEntity<Long>{

    @Valid
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Valid
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prefecture_id")
    private Prefecture prefecture;

    @NotEmpty(message="{NotEmpty.cnpj}")
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(name="is_active")
    private boolean isActive;

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
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
