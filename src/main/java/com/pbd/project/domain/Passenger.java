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
    @Column(name ="date_of_birth", columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @NotNull(message = "{NotNull.people.gender}")
    @Column(length = 1)
    @Enumerated(EnumType.STRING)
    private Gender Gender;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @NotNull(message = "{NotNull.people.cellPhone}")
    @Column(name="cell_phone", length = 15)// (99) 99999-9999
    private String cellPhone;

    @ManyToOne
    @JoinColumn(name = "health_center_id", nullable = false)
    private HealthCenter healthCenter;

    @Column(length = 14) // (99) 9999-9999
    private String landline;
    private Boolean isActive;

    @Column(name="warning_count")
    private Integer warningCount;

    @Column(columnDefinition = "TEXT")
    private String observation;




}
