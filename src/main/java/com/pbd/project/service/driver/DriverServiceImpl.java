package com.pbd.project.service.driver;

import com.pbd.project.dao.driver.DriverDao;
import com.pbd.project.domain.Driver;
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
    public Driver findById(Long id) {
        return driverDao.findById(id).get();
    }

    @Override
    public List<Driver> findAll() {
        return driverDao.findAll();
    }

    @Override
    public List<Driver> findAvailable(Long idHealthCenter, boolean isAvailable, boolean isActive) {
        return driverDao.findAvailables(idHealthCenter, isAvailable, isActive);
    }
}
