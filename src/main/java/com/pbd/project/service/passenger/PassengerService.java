package com.pbd.project.service.passenger;

import com.pbd.project.domain.Address;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Passenger;

import java.util.List;

public interface PassengerService {
    void save(Passenger passenger);
    void update(Passenger passenger);
    Passenger findById(Long id);
    Passenger findPassengerBySus(String sus);
    Passenger findPassengerByRg(String rg);
    Passenger findPassengerByAddressAndId(Address address, Long id);
    List<Passenger> findAll();
    List<Passenger> findPassengerByHealthCenterAndActive(HealthCenter healthCenter, boolean active);
    List<Passenger> findPassengerByHealthCenter(HealthCenter healthCenter);

    void changePassengerStatus(Passenger passenger, boolean active);
    List<Passenger> getModelAttribute();


}
