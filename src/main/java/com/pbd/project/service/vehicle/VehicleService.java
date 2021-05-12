package com.pbd.project.service.vehicle;

import com.pbd.project.domain.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle save(Vehicle vehicle);
    void delete(Long id);
    Vehicle findById(Long id);
    List<Vehicle> findAll();
    List<Vehicle> findAvailable(Long idHealthCenter, boolean isAvailable, boolean isActive);
}
