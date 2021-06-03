package com.pbd.project.dao.passenger;

import com.pbd.project.domain.Address;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerDao extends JpaRepository<Passenger, Long> {
    List<Passenger> findPassengerByHealthCenterAndActive(HealthCenter healthCenter, boolean active);
    List<Passenger> findPassengerByHealthCenter(HealthCenter healthCenter);
    Passenger findPassengerBySus(String sus);
    Passenger findPassengerByRg(String rg);
    Passenger findPassengerByAddressAndId(Address address, Long id);

    Page<Passenger> findPassengerByNameContainsIgnoreCase(Pageable pageable, String name);
    Page<Passenger> findPassengerByHealthCenter(Pageable pageable, HealthCenter healthCenter);
    Page<Passenger> findPassengerByHealthCenterAndActive(Pageable pageable,HealthCenter healthCenter, boolean active);
    Page<Passenger> findPassengerByHealthCenterAndNameContainsIgnoreCase(Pageable pageable,
                                                                         HealthCenter healthCenter,
                                                                         String name);

    Page<Passenger> findPassengerByHealthCenterAndActiveAndNameContainsIgnoreCase(Pageable pageable,
                                                                                  HealthCenter healthCenter,
                                                                                  boolean isActive,
                                                                                  String name);
}
