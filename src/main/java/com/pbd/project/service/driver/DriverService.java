package com.pbd.project.service.driver;


import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Vehicle;

import java.util.List;

public interface DriverService {
    Driver save(Driver driver);
    void delete(Long id);
    Driver findDriverById(Long id);
    List<Driver> getAllDrivers();
    List<Driver> getAllDriversByAvailableAndActive(Long idHealthCenter, boolean isAvailable, boolean isActive);
    List<Driver> getAllDriversByHealthCenter(HealthCenter healthCenter);
    void changeAvailable(Driver driver);
}
