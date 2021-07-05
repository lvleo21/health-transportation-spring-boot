package com.pbd.project.service.travel;

import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Travel;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface TravelService {
    void save(Travel travel);
    boolean update(Travel travel);
    void delete(Travel travel);
    Travel findById(Long id);
    List<Travel> findAll();
    List<Travel> findByHealthCenter(HealthCenter healthCenter);
    List<Travel> findTravelByDriver(Driver driver);

    boolean changeTravelStatus(Long id);

    Page<Travel> findAll(int currentPage);
    Page<Travel> findTravelByHealthCenter(int currentPage, HealthCenter healthCenter);
    Page<Travel> findTravelByDepartureDate(int currentPage, LocalDate departureDate);
    Page<Travel> findTravelByHealthCenterAndDepartureDate(int currentPage,
                                                          HealthCenter healthCenter,
                                                          LocalDate departureDate);
}

