package com.pbd.project.dao.travel;

import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TravelDao extends JpaRepository<Travel, Long> {
    List<Travel> findTravelByHealthCenter(HealthCenter healthCenter);
    List<Travel> findTravelByDriver(Driver driver);


    //TODO: Buscar todas as viagens
    Page<Travel> findAll(Pageable pageable);

    //TODO: Buscar todas as viagens por health center
    Page<Travel> findTravelByHealthCenter(Pageable pageable, HealthCenter healthCenter);

    //TODO: Buscar todas as viagens por data
    Page<Travel> findTravelByDepartureDate(Pageable pageable, LocalDate departureDate);

    //TODO: Buscar todas as viagens por data e healthCenter
    Page<Travel> findTravelByHealthCenterAndDepartureDate(Pageable pageable,
                                                          HealthCenter healthCenter,
                                                          LocalDate localDate);

}
