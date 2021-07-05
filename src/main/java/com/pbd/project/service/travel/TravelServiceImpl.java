package com.pbd.project.service.travel;

import com.pbd.project.dao.travel.TravelDao;
import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Travel;
import com.pbd.project.domain.enums.TravelStatus;
import com.pbd.project.service.driver.DriverService;
import com.pbd.project.service.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        this.changeDriverAndVehicleStatus(travel);
        travelDao.save(travel);
    }

    @Override
    public boolean update(Travel travel) {
        Travel travelInDatabase = this.findById(travel.getId());

        if(travelInDatabase.getVehicle().getId() != travel.getVehicle().getId()){
            if(travelInDatabase.getQntPassengers() > travel.getVehicle().getCapacity() ){
                return false;
            }

            vehicleService.changeAvailable(travelInDatabase.getVehicle());
            vehicleService.changeAvailable(travel.getVehicle());
        }

        if(travelInDatabase.getDriver().getId() != travel.getDriver().getId()){
            driverService.changeAvailable(travelInDatabase.getDriver());
            driverService.changeAvailable(travel.getDriver());
        }

        travelDao.save(travel);
        return true;
    }

    @Override
    public void delete(Travel travel) {
        this.changeDriverAndVehicleStatus(travel);
        travelDao.delete(travel);
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

    @Override
    public List<Travel> findTravelByDriver(Driver driver) {
        return travelDao.findTravelByDriver(driver);
    }

    @Override
    public boolean changeTravelStatus(Long id) {
        Travel travel = this.findById(id);

        switch (travel.getStatus()){
            case AGUARDANDO:
                travel.setStatus(TravelStatus.EM_TRANSITO);
                break;
            case EM_TRANSITO:
                travel.setStatus(TravelStatus.CONCLUIDO);
                this.changeDriverAndVehicleStatus(travel);
                travel.setReturnDate(LocalDate.now());

                break;
            case CONCLUIDO:
                return false;
        }

        this.travelDao.save(travel);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Travel> findAll(int currentPage) {
        return travelDao.findAll(this.getPageable(currentPage));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Travel> findTravelByHealthCenter(int currentPage, HealthCenter healthCenter) {
        return travelDao.findTravelByHealthCenter(this.getPageable(currentPage), healthCenter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Travel> findTravelByDepartureDate(int currentPage, LocalDate departureDate) {
        return travelDao.findTravelByDepartureDate(this.getPageable(currentPage), departureDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Travel> findTravelByHealthCenterAndDepartureDate(int currentPage, HealthCenter healthCenter, LocalDate departureDate) {
        return travelDao.findTravelByHealthCenterAndDepartureDate(
                this.getPageable(currentPage),
                healthCenter,
                departureDate
        );
    }


    public void changeDriverAndVehicleStatus(Travel travel){
        driverService.changeAvailable(travel.getDriver());
        vehicleService.changeAvailable(travel.getVehicle());
    }


    public Pageable getPageable(int currentPage){
        return PageRequest.of(currentPage, 50, Sort.by("departureDate").descending());
    }

}
