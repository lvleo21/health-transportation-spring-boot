package com.pbd.project.service.vehicle;

import com.pbd.project.dao.vehicle.VehicleDao;
import com.pbd.project.domain.HealthCenter;
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
    @Transactional(readOnly = true)
    public Vehicle findById(Long id) {
        return vehicleDao.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle findByPlaque(String plaque) {
        return vehicleDao.findVehicleByPlaque(plaque);
    }


//    public boolean findRelatedTravels(Long id) {
//        return vehicleDao.findRelatedTravels(id);
//    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        return vehicleDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> findByHealthcenter(HealthCenter healthCenter) {
        return vehicleDao.findVehicleByHealthCenter(healthCenter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> findAvailable(Long idHealthCenter, boolean isAvailable, boolean isActive) {
        return vehicleDao.findAvailables(idHealthCenter, isAvailable, isActive);
    }

    @Override
    public void changeActive(Vehicle vehicle) {
        vehicle.setActive(!vehicle.isActive());
        this.save(vehicle);
    }

    @Override
    public void changeAvailable(Vehicle vehicle) {
        vehicle.setAvailable(vehicle.isAvailable() ? false : true);
        this.save(vehicle);
    }
}
