package com.pbd.project.service.travel;

import com.pbd.project.dao.travel.TravelDao;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Travel;
import com.pbd.project.domain.enums.TravelStatus;
import com.pbd.project.service.driver.DriverService;
import com.pbd.project.service.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TravelServiceImpl implements TravelService {

    @Autowired
    private TravelDao travelDao;

    @Autowired
    private DriverService driverService;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void save(Travel travel) {
        driverService.changeAvailable(travel.getDriver());
        vehicleService.changeAvailable(travel.getVehicle());

        travelDao.save(travel);
    }

    @Override
    public void update(Travel travel) {

        if(travel.getStatus().equals(TravelStatus.CONCLUIDO)){
            driverService.changeAvailable(travel.getDriver());
            vehicleService.changeAvailable(travel.getVehicle());
            travel.setReturnDate(LocalDate.now());
        }

        travelDao.save(travel);
    }

    @Override
    public void delete(Long id) {
        travelDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Travel findById(Long id) {
        return travelDao.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Travel> findAll() {
        return travelDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Travel> findByHealthCenter(HealthCenter healthCenter) {
        return travelDao.findTravelByHealthCenter(healthCenter);
    }
}
