package com.pbd.project.domain;

import com.pbd.project.domain.enums.UF;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

//    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY, optional = false)
//    private Prefecture prefecture;
//
//    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY, optional = false)
//    private HealthCenter healthCenter;
//
//

}
