package com.pbd.project.dao.passenger;

import com.pbd.project.domain.Address;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerDao extends JpaRepository<Passenger, Long> {
    List<Passenger> findPassengerByHealthCenterAndActive(HealthCenter healthCenter, boolean active);
    Passenger findPassengerBySus(String sus);
    Passenger findPassengerByRg(String rg);
    Passenger findPassengerByAddressAndId(Address address, Long id);
}
