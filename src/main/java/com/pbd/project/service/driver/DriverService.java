package com.pbd.project.service.driver;


import com.pbd.project.domain.Driver;
import com.pbd.project.domain.Vehicle;

import java.util.List;

public interface DriverService {
    Driver save(Driver driver);
    void delete(Long id);
    Driver findById(Long id);
    List<Driver> findAll();
    List<Driver> findAvailable(Long idHealthCenter, boolean isAvailable, boolean isActive);
}
