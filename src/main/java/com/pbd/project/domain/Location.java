package com.pbd.project.domain;

import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class Location extends AbstractEntity<Long>{

    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Travel travel;
    private String category;
    private String transition;
    private boolean isPreferential = false;
    private String destinationHospital;
    private LocalDate createdAt = LocalDate.now();



}
