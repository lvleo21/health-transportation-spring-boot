package com.pbd.project.service.vehicle;

import com.pbd.project.dao.vehicle.VehicleDao;
import com.pbd.project.domain.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleDao.save(vehicle);
    }

    @Override
    public void delete(Long id) {
        vehicleDao.deleteById(id);
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicleDao.findById(id).get();
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleDao.findAll();
    }

    @Override
    public List<Vehicle> findAvailable(Long idHealthCenter, boolean isAvailable, boolean isActive) {
        return vehicleDao.findAvailables(idHealthCenter, isAvailable, isActive);
    }
}
