package com.pbd.project.service.driver;

import com.pbd.project.dao.driver.DriverDao;
import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Vehicle;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DriverServiceImpl implements DriverService{

    @Autowired
    private DriverDao driverDao;

    @Override
    public Driver save(Driver driver) {
        return driverDao.save(driver);
    }

    @Override
    public void delete(Long id) {
        driverDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Driver findDriverById(Long id) {
        return driverDao.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Driver> getAllDrivers() {
        return driverDao.findAllByOrderByNameAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Driver> getAllDriversByAvailableAndActive(Long idHealthCenter, boolean isAvailable, boolean isActive) {
        return driverDao.findAvailables(idHealthCenter, isAvailable, isActive);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Driver> getAllDriversByHealthCenter(HealthCenter healthCenter) {
        return driverDao.findDriverByHealthCenter(healthCenter);
    }

    @Override
    public void changeAvailable(Driver driver) {
        driver.setAvailable(driver.isAvailable() ? false : true);
        this.save(driver);
    }


}
