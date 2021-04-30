package com.pbd.project.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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

    @OneToMany(mappedBy = "healthCenter", fetch = FetchType.LAZY)
    private List<User> users;



}
