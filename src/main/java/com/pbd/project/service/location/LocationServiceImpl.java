package com.pbd.project.service.location;

import com.pbd.project.dao.location.LocationDao;
import com.pbd.project.domain.Location;
import com.pbd.project.domain.Travel;
import com.pbd.project.domain.enums.TravelStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationServiceImpl implements LocationService{

    @Autowired
    private LocationDao locationDao;

    @Override
    public void save(Location location) {
        locationDao.save(location);
    }

    @Override
    public void update(Location location) {
        locationDao.save(location);
    }

    @Override
    public void delete(Location location) {
        locationDao.delete(location);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> findByTravel(Travel travel) {
        return locationDao.findByTravel(travel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> findAll() {
        return locationDao.findAll();
    }

    @Override
    public List<Location> findLocationByPassengerAndTravelStatus(Long idPassenger, String travelStatus) {
        return locationDao.findLocationByPassengerAndTravelStatus(idPassenger, travelStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> findLocationByPassengerName(Long travelId, String name) {
        return locationDao.findLocationByPassengerName(travelId, name);
    }

    @Override
    @Transactional(readOnly = true)
    public Location findById(Long id) {
        return locationDao.findById(id).get();
    }
}
