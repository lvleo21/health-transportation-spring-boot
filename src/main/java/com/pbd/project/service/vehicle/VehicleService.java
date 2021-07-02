package com.pbd.project.service.vehicle;

import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle save(Vehicle vehicle);
    void delete(Long id);
    Vehicle findById(Long id);
    Vehicle findByPlaque(String plaque);

//    boolean findRelatedTravels(Long id);
    List<Vehicle> findAll();
    List<Vehicle> findVehicleByName(String name);
    List<Vehicle> findVehicleByHealthCenterAndName(HealthCenter healthCenter, String name);
    List<Vehicle> findByHealthcenter(HealthCenter healthCenter);
    List<Vehicle> findAvailable(Long idHealthCenter, boolean isAvailable, boolean isActive);

    void changeActive(Vehicle vehicle);
    void changeAvailable(Vehicle vehicle);
}
