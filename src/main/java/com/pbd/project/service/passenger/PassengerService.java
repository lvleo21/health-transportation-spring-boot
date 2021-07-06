package com.pbd.project.service.passenger;

import com.pbd.project.domain.Address;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    void changePassengerStatus(Passenger passenger);
    List<Passenger> getModelAttribute();

    void deletePassenger(Long id);

    //! Paginação
    Page<Passenger> findAll(int currentPage);
    Page<Passenger> findPassengerByName(int currentPage, String name);
    Page<Passenger> findPassengerByHealthCenter(int currentPage, HealthCenter healthCenter);
    Page<Passenger> findPassengerByHealthCenterAndActive(int currentPage, HealthCenter healthCenter, boolean active);
    Page<Passenger> findPassengerByHealthCenterAndNameContainsIgnoreCase(
            int currentPage,
            HealthCenter healthCenter,
            String name
    );
    Page<Passenger> findPassengerByHealthCenterAndActiveAndNameContainsIgnoreCase(int currentPage,
                                                                                  HealthCenter healthCenter,
                                                                                  boolean isActive,
                                                                                  String name);

}
