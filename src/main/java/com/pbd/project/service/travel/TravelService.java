package com.pbd.project.service.travel;

import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Travel;

import java.util.List;

public interface TravelService {
    void save(Travel travel);
    void update(Travel travel);
    void delete(Travel travel);
    Travel findById(Long id);
    List<Travel> findAll();
    List<Travel> findByHealthCenter(HealthCenter healthCenter);
    List<Travel> findTravelByDriver(Driver driver);
}
