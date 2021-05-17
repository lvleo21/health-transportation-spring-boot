package com.pbd.project.service.location;


import com.pbd.project.domain.Location;
import com.pbd.project.domain.Passenger;
import com.pbd.project.domain.Travel;

import java.util.List;

public interface LocationService {
    void save(Location location);
    void update(Location location);
    void delete(Location location);
    Location findById(Long id);
    List<Location> findByTravel(Travel travel);
    List<Location> findAll();
    List<Location> findLocationByPassengerAndTravelStatus(Long idPassenger, String travelStatus);
}
