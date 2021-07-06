package com.pbd.project.service.driver;


import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.User;
import com.pbd.project.domain.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DriverService {
    Driver save(Driver driver);
    boolean delete(Long id);
    Driver findDriverById(Long id);
    List<Driver> getAllDrivers();
    List<Driver> getAllDriversByAvailableAndActive(Long idHealthCenter, boolean isAvailable, boolean isActive);
    List<Driver> getAllDriversByHealthCenter(HealthCenter healthCenter);
    void changeAvailable(Driver driver);
    void changeActive(Driver driver);
    Page<Driver> getPaginatedDrivers(int currentPage);
    Page<Driver> getDriversByHealthCenter(int currentPage, HealthCenter healthCenter);
    Page<Driver> findDriversByNameAndHealthCenter(int currentPage, HealthCenter healthCenter, String name);
    Page<Driver> findDriversByName(int currentPage, String name);
    Page<Driver> getDrivers(int currentPage, String name, boolean isStaff, HealthCenter healthCenter);


    //! Para ADMIN no getModelAttributes
    List<Driver> findDriverByActiveAndAvailable(boolean active, boolean available);
}
