package com.pbd.project.dao.travel;

import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelDao extends JpaRepository<Travel, Long> {
    List<Travel> findTravelByHealthCenter(HealthCenter healthCenter);
    List<Travel> findTravelByDriver(Driver driver);
}
