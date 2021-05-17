package com.pbd.project.service.passenger;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Passenger;

import java.util.List;

public interface PassengerService {
    void save(Passenger passenger);
    void update(Passenger passenger);
    Passenger findById(Long id);
    List<Passenger> findAll();
    List<Passenger> findByHealthCenter(HealthCenter healthCenter);
}
